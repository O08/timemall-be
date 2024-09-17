package com.norm.timemall.app.base.handlers;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.entity.PasswordResetDTO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.EmailMessageTopicEnum;
import com.norm.timemall.app.base.enums.RichTextConfigEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.EmailMessage;
import com.norm.timemall.app.base.mo.RichTextConfig;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.EmailMessageService;
import com.norm.timemall.app.base.service.RichTextConfigService;
import com.norm.timemall.app.base.util.SecureCheckUtil;
import com.norm.timemall.app.base.util.zoho.ZohoEmailApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 密码重置
 */
@Component
public class PasswordResetHandler {
    @Autowired
    private RichTextConfigService richTextConfigService;
    @Autowired
    private EmailMessageService emailMessageService;

    @Autowired
    private AccountService accountService;
    @Autowired
    private ZohoEmailApi zohoEmailApi;



    @Autowired
    private EnvBean env;


    /**
     * @param email , email of  recipient
     */
    public void doSendEmailOfPasswordReset(String email){
        // 24h 内如果已经发送了5次， 禁止发送
        Long cnt = emailMessageService.countMessageIn24Hour(EmailMessageTopicEnum.EMAIL_PASSWORD_RESET.getTopic(),
                email);
        if(cnt == 5)
        {
            throw new ErrorCodeException(CodeEnum.EMAIL_LIMIT);
        }
        // 查询模版
        RichTextConfig emailHtmlConfig = richTextConfigService.getRichTextConfig(RichTextConfigEnum.EMAIL_PASSWORD_RESET.getType(),
                RichTextConfigEnum.EMAIL_PASSWORD_RESET.getNo());

        if(StrUtil.isEmptyIfStr(emailHtmlConfig)){
            throw new ErrorCodeException(CodeEnum.EMAIL_TEMPLATE_NOT_CONFIG);
        }
        // 签发token
        Map<String, Object> identityMap = new HashMap<>();
        identityMap.put("email",email);
        identityMap.put("time",System.currentTimeMillis());
        String token = SecureCheckUtil.encryptIdentity(new Gson().toJson(identityMap));
        // 生成邮件内容
        String content = emailHtmlConfig.getContent()
                .replace("#{webaddress}", env.getWebsite())
//                .replace("#{username}",customer.getUsername())
                .replace("#{resetPasswordLink}",env.getWebsite()+"password-change.html?token="+token);
        // 发送邮件 1 发送对象  2 主题 3  html
        zohoEmailApi.sendNoreplyEmail(email,"密码重置",content);
        // 存储发送记录
        EmailMessage message = new EmailMessage();
        message.setSender("sys")
                .setRecipient(email)
                .setTopic(EmailMessageTopicEnum.EMAIL_PASSWORD_RESET.getTopic())
                .setCreateAt(new Date());

        emailMessageService.save(message);
    }

    public void doPasswordReset(PasswordResetDTO passwordResetDTO){
        String decryptToken = SecureCheckUtil.decryptIdentity(passwordResetDTO.getToken());
        JsonObject identity = JsonParser.parseString(decryptToken).getAsJsonObject();
        long time =  identity.get("time").getAsLong();
        String email = identity.get("email").getAsString();
        // if token expired ,return. interval 1h
        boolean expired = System.currentTimeMillis() - time > env.getTokenOut();
        if(expired){
            throw new ErrorCodeException(CodeEnum.INVALID_LINK);
        }
        // reset password
        String encryptedPassword= new BCryptPasswordEncoder().encode(passwordResetDTO.getPassword());
        accountService.modifiedPasswordByUserName(encryptedPassword,email);

        // send password updated email
        sendPasswordUpdatedEmail(email);

    }
    private void sendPasswordUpdatedEmail(String email){
        // 查询模版
        RichTextConfig emailHtmlConfig = richTextConfigService.getRichTextConfig(
                RichTextConfigEnum.EMAIL_PASSWORD_UPDATED.getType(),
                RichTextConfigEnum.EMAIL_PASSWORD_UPDATED.getNo());

        if(StrUtil.isEmptyIfStr(emailHtmlConfig)){
            throw new ErrorCodeException(CodeEnum.EMAIL_TEMPLATE_NOT_CONFIG);
        }

        // 生成邮件内容
        String content = emailHtmlConfig.getContent()
                .replace("#{webaddress}", env.getWebsite())
                .replace("#{supportLink}",env.getWebsite()+"mall/contact-us.html");
        // 发送邮件 1 发送对象  2 主题 3  html
        zohoEmailApi.sendNoreplyEmail(email,"密码重置成功",content);
    }

}
