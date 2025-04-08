package com.norm.timemall.app.ms.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Commission;
import com.norm.timemall.app.base.mo.CommissionWsMsg;
import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import com.norm.timemall.app.base.pojo.DefaultTextMessage;
import com.norm.timemall.app.ms.mapper.MsCommissionMapper;
import com.norm.timemall.app.ms.mapper.MsCommissionWsMsgMapper;
import com.norm.timemall.app.ms.service.MsCommissionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MsCommissionMessageServiceImpl implements MsCommissionMessageService {
    @Autowired
    private MsCommissionWsMsgMapper msCommissionWsMsgMapper;
    @Autowired
    private MsCommissionMapper msCommissionMapper;

    @Override
    public MsDefaultEvent findEvent(String channel) {

        return msCommissionWsMsgMapper.selectEventByChannelId(channel);

    }

    @Override
    public void storeTextMessage(String channel, MsStoreDefaultTextMessageDTO dto) {

        DefaultTextMessage textMessage = new DefaultTextMessage();
        textMessage.setContent(dto.getMsg());
        Gson gson = new Gson();
        CommissionWsMsg msg = new CommissionWsMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setCommissionId(channel)
                .setAuthorId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setMsgType(dto.getMsgType())
                .setMsg(gson.toJson(textMessage))
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        msCommissionWsMsgMapper.insert(msg);

    }

    @Override
    public void storeImageMessage(String channel, String msgJson,  String msgType) {

        CommissionWsMsg msg = new CommissionWsMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setCommissionId(channel)
                .setAuthorId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msCommissionWsMsgMapper.insert(msg);

    }

    @Override
    public void storeAttachmentMessage(String channel, String msgJson,  String msgType) {

        CommissionWsMsg msg = new CommissionWsMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setCommissionId(channel)
                .setAuthorId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msCommissionWsMsgMapper.insert(msg);

    }

    @Override
    public boolean channelExist(String channel) {
        LambdaQueryWrapper<Commission> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Commission::getId,channel);
        return msCommissionMapper.exists(wrapper);
    }
}
