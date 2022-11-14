package com.norm.timemall.app.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.norm.timemall.app.base.mapper.EmailMessageMapper;
import com.norm.timemall.app.base.mo.EmailMessage;
import com.norm.timemall.app.base.service.EmailMessageService;
import org.springframework.stereotype.Service;

@Service
public class EmailMessageServiceImpl extends ServiceImpl<EmailMessageMapper, EmailMessage> implements EmailMessageService {
    @Override
    public Long countMessageIn24Hour(String topic, String email) {
        LambdaQueryWrapper<EmailMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EmailMessage::getTopic,topic)
               .eq(EmailMessage::getRecipient,email);
        Long cnt = this.baseMapper.selectCount(wrapper);

        return cnt;
    }

    @Override
    public EmailMessage getLastestOneByRecipientAndBodyAndTopic(String email, String body,String topic) {
        LambdaQueryWrapper<EmailMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EmailMessage::getTopic,topic)
                .eq(EmailMessage::getRecipient,email)
                .eq(EmailMessage::getBody,body)
                .orderByDesc(EmailMessage::getCreateAt)
                .last("limit 1");
        return this.baseMapper.selectOne(wrapper);

    }
}
