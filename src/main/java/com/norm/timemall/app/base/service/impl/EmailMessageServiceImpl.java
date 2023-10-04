package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.norm.timemall.app.base.mapper.EmailMessageMapper;
import com.norm.timemall.app.base.mo.EmailMessage;
import com.norm.timemall.app.base.service.EmailMessageService;
import com.norm.timemall.app.base.util.mate.MybatisMateEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EmailMessageServiceImpl extends ServiceImpl<EmailMessageMapper, EmailMessage> implements EmailMessageService {
   @Autowired
   private MybatisMateEncryptor mybatisMateEncryptor;
    @Override
    public Long countMessageIn24Hour(String topic, String email) {
        LambdaQueryWrapper<EmailMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EmailMessage::getTopic,topic)
               .eq(EmailMessage::getRecipient,mybatisMateEncryptor.defaultEncrypt(email))
                .ge(EmailMessage::getCreateAt, DateUtil.beginOfDay(new Date()));
        Long cnt = this.baseMapper.selectCount(wrapper);

        return cnt;
    }

    @Override
    public EmailMessage getLastestOneByRecipientAndBodyAndTopic(String email, String body,String topic) {
        LambdaQueryWrapper<EmailMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EmailMessage::getTopic,topic)
                .eq(EmailMessage::getRecipient,mybatisMateEncryptor.defaultEncrypt(email))
                .eq(EmailMessage::getBody,mybatisMateEncryptor.defaultEncrypt(body))
                .orderByDesc(EmailMessage::getCreateAt)
                .last("limit 1");
        return this.baseMapper.selectOne(wrapper);

    }

    @Override
    public Long countMessageUsingRef(String topic, String email, String ref) {
        LambdaQueryWrapper<EmailMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(EmailMessage::getTopic,topic)
                .eq(EmailMessage::getRecipient,mybatisMateEncryptor.defaultEncrypt(email))
                .eq(EmailMessage::getRef,ref);
        Long cnt = this.baseMapper.selectCount(wrapper);

        return cnt;
    }
}
