package com.norm.timemall.app.base.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.norm.timemall.app.base.mapper.BaseSmsMessageMapper;
import com.norm.timemall.app.base.mo.EmailMessage;
import com.norm.timemall.app.base.mo.SmsMessage;
import com.norm.timemall.app.base.service.SmsMessageService;
import com.norm.timemall.app.base.util.mate.MybatisMateEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class SmsMessageServiceImpl extends ServiceImpl< BaseSmsMessageMapper,SmsMessage> implements SmsMessageService {
    @Autowired
    private MybatisMateEncryptor mybatisMateEncryptor;

    @Override
    public Long countMessageIn24Hour(String topic,String phone, String ipAddress) {

        LambdaQueryWrapper<SmsMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SmsMessage::getTopic,topic)
                .ge(SmsMessage::getCreateAt, DateUtil.beginOfDay(new Date()));

        wrapper.and(w -> w.eq(SmsMessage::getIp,ipAddress)
                .or()
                .eq(SmsMessage::getPhone,mybatisMateEncryptor.defaultEncrypt(phone)));

        return this.baseMapper.selectCount(wrapper);

    }

    @Override
    public SmsMessage getLastestOneByPhoneAndBodyAndTopic(String topic, String emailOrPhone, String body) {

        LambdaQueryWrapper<SmsMessage> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(SmsMessage::getTopic,topic)
                .eq(SmsMessage::getPhone,mybatisMateEncryptor.defaultEncrypt(emailOrPhone))
                .eq(SmsMessage::getBody,mybatisMateEncryptor.defaultEncrypt(body))
                .orderByDesc(SmsMessage::getCreateAt)
                .last("limit 1");
        return this.baseMapper.selectOne(wrapper);

    }
}
