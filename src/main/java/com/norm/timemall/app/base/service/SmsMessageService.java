package com.norm.timemall.app.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.norm.timemall.app.base.mo.SmsMessage;
import org.springframework.stereotype.Service;

@Service
public interface SmsMessageService extends IService<SmsMessage> {
    Long countMessageIn24Hour(String topic,String phone, String ipAddress);

    SmsMessage getLastestOneByPhoneAndBodyAndTopic(String topic, String emailOrPhone, String qrcode);
}
