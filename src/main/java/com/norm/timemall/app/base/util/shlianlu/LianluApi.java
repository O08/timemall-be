package com.norm.timemall.app.base.util.shlianlu;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LianluApi {
    @Autowired
    private LianluResource lianluResource;


    public JSONObject smsNormalSendOne(String phone, String sessionContext) throws Exception {
        Credential credential = new Credential(lianluResource.getMchId(),
                lianluResource.getAppId(), lianluResource.getAppKey());
        SmsSend s = new SmsSend();
        s.setPhoneNumberSet(new String[] {phone});
        s.setSignName("【群巅科技】");
        s.setSessionContext(sessionContext);

       return s.normalSend(credential, s);

    }
    public JSONObject smsTemplateSendOne(String phone,String templateId,String[] templateParamSet) throws Exception {
        Credential credential = new Credential(lianluResource.getMchId(),
                lianluResource.getAppId(), lianluResource.getAppKey());
        SmsSend s = new SmsSend();
        s.setPhoneNumberSet(new String[] {phone});
        s.setSignName("【群巅科技】");
        s.setTemplateId(templateId);
        s.setTemplateParamSet(templateParamSet);

        return  s.templateSend(credential,s);
    }

}
