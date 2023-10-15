package com.norm.timemall.app.base.handlers;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.EmailMessageTopicEnum;
import com.norm.timemall.app.base.enums.RichTextConfigEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.EmailMessage;
import com.norm.timemall.app.base.mo.RichTextConfig;
import com.norm.timemall.app.base.service.EmailMessageService;
import com.norm.timemall.app.base.service.RichTextConfigService;
import com.norm.timemall.app.base.util.zoho.ZohoEmailApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Date;


/**
 * 验证码功能逻辑
 */
@Component
public class VerificationCodeHandler {
    @Autowired
    private RichTextConfigService richTextConfigService;
    @Autowired
    private EmailMessageService emailMessageService;

    @Autowired
    private ZohoEmailApi zohoEmailApi;

    @Autowired
    private EnvBean env;

    /**
     * @param email , email of  recipient
     */
    public void doSendEmailCode(String email){
        // 24h 内如果已经发送了5次， 禁止发送
        Long cnt = emailMessageService.countMessageIn24Hour(EmailMessageTopicEnum.EMAIL_VERIFICATION_CODE.getTopic(),
                email);
        if(cnt == 5)
        {
            throw new ErrorCodeException(CodeEnum.EMAIL_LIMIT);
        }
        // 查询模版
        RichTextConfig emailHtmlConfig = richTextConfigService.getRichTextConfig(RichTextConfigEnum.EMAIL_VERIFICATION_CODE.getType(),
                RichTextConfigEnum.EMAIL_VERIFICATION_CODE.getNo());

        if(StrUtil.isEmptyIfStr(emailHtmlConfig)){
            throw new ErrorCodeException(CodeEnum.EMAIL_TEMPLATE_NOT_CONFIG);
        }
        // 生成验证码
        String qrcode = RandomUtil.randomNumbers(6);
        EmailMessage message = new EmailMessage();
        message.setSender("sys").
                setBody(qrcode).
                setRecipient(email).
                setTopic(EmailMessageTopicEnum.EMAIL_VERIFICATION_CODE.getTopic()).
                setCreateAt(new Date());
        // 保存消息记录
        emailMessageService.save(message);
        // 生成邮件内容
        String content = emailHtmlConfig.getContent()
                .replace("#{webaddress}", env.getWebsite())
                .replace("#{qrcode}", qrcode);
        // 发送邮件 1 发送对象  2 主题 3  html
        zohoEmailApi.sendNoreplyEmail(email,"您的邮箱验证码：" + qrcode,content);
    }

    /**
     * 验证码有效性验证
     * @param email
     * @param code
     * @return
     */

    public boolean verify(String email, String code)
    {
        EmailMessage message = emailMessageService.getLastestOneByRecipientAndBodyAndTopic(email, code,
                EmailMessageTopicEnum.EMAIL_VERIFICATION_CODE.getTopic());
        // 验证码一小时内有效
        return message != null && DateUtil.between(message.getCreateAt(), new Date(), DateUnit.HOUR) <= 1;
    }
}
