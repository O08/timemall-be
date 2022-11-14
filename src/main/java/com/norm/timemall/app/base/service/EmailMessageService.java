package com.norm.timemall.app.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.norm.timemall.app.base.mo.EmailMessage;
import org.springframework.stereotype.Service;

@Service
public interface EmailMessageService extends IService<EmailMessage> {
    Long countMessageIn24Hour(String topic,String email);

    EmailMessage getLastestOneByRecipientAndBodyAndTopic(String email, String body,String topic);
}
