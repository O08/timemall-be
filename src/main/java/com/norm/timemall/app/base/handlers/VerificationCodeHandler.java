package com.norm.timemall.app.base.handlers;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.EmailMessageTopicEnum;
import com.norm.timemall.app.base.enums.RichTextConfigEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.EmailMessage;
import com.norm.timemall.app.base.mo.RichTextConfig;
import com.norm.timemall.app.base.mo.SmsMessage;
import com.norm.timemall.app.base.service.EmailMessageService;
import com.norm.timemall.app.base.service.RichTextConfigService;
import com.norm.timemall.app.base.service.SmsMessageService;
import com.norm.timemall.app.base.util.shlianlu.LianluApi;
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
    private SmsMessageService smsMessageService;


    @Autowired
    private ZohoEmailApi zohoEmailApi;

    @Autowired
    private LianluApi lianluApi;

    @Autowired
    private EnvBean env;

    /**
     * @param email , email of  recipient
     */
    public void doSendEmailCode(String email){
        // 24h 内如果已经发送了5次， 禁止发送
        Long cnt = emailMessageService.countMessageIn24Hour(EmailMessageTopicEnum.EMAIL_VERIFICATION_CODE.getTopic(),
                email);
        if(cnt >= 5)
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
        zohoEmailApi.sendNoreplyEmail(email,"邮箱注册验证码：" + qrcode,content);
    }

    /**
     * 验证码有效性验证
     * @param email
     * @param code
     * @return
     */

    public boolean verifyEmailCode(String email, String code)
    {
        EmailMessage message = emailMessageService.getLastestOneByRecipientAndBodyAndTopic(email, code,
                EmailMessageTopicEnum.EMAIL_VERIFICATION_CODE.getTopic());
        // 验证码一小时内有效
        return message != null && DateUtil.between(message.getCreateAt(), new Date(), DateUnit.HOUR) <= 1;
    }

    public void doSendPhoneCode(String phone,String ipAddress) throws Exception {

        // 24h 内如果已经发送了5次， 禁止发送
        Long cnt = smsMessageService.countMessageIn24Hour(RichTextConfigEnum.PHONE_VERIFICATION_CODE.getNo(), phone,ipAddress);
        if(cnt >= 5)
        {
            throw new ErrorCodeException(CodeEnum.PHONE_LIMIT);
        }

        // 查询模版
        RichTextConfig templateConfig = richTextConfigService.getRichTextConfig(RichTextConfigEnum.PHONE_VERIFICATION_CODE.getType(),
                RichTextConfigEnum.PHONE_VERIFICATION_CODE.getNo());

        if(StrUtil.isEmptyIfStr(templateConfig)){
            throw new ErrorCodeException(CodeEnum.PHONE_TEMPLATE_NOT_CONFIG);
        }

        // 生成验证码
        String qrcode = RandomUtil.randomNumbers(6);

        // 生成短信内容

        JSONObject resultJson = lianluApi.smsTemplateSendOne(phone, templateConfig.getContent(), new String[]{qrcode});

        SmsMessage smsMessage = new SmsMessage();
        smsMessage.setPhone(phone)
                .setTopic(RichTextConfigEnum.PHONE_VERIFICATION_CODE.getNo())
                    .setIp(ipAddress)
                    .setId(IdUtil.simpleUUID())
                    .setBody(qrcode)
                    .setSendResponse(resultJson.toJSONString())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());

        // 保存消息记录
        smsMessageService.save(smsMessage);




    }

    public boolean uniVerify(String emailOrPhone, String qrcode) {
        boolean isMobile = Validator.isMobile(emailOrPhone);
        boolean isEmail = Validator.isEmail(emailOrPhone);
        if(!(isEmail || isMobile)){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_DISABLE);
        }
        if(isEmail){
            return verifyEmailCode(emailOrPhone,qrcode);
        }
        return verifySmsCode(emailOrPhone,qrcode);
    }
    private boolean verifySmsCode(String emailOrPhone, String qrcode) {

        SmsMessage smsMessage= smsMessageService.getLastestOneByPhoneAndBodyAndTopic(RichTextConfigEnum.PHONE_VERIFICATION_CODE.getNo(),emailOrPhone,qrcode);
        // 验证码一小时内有效
        return smsMessage != null && DateUtil.between(smsMessage.getCreateAt(), new Date(), DateUnit.MINUTE) <= 5;

    }


}
