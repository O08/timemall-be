package com.norm.timemall.app.ms.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.ChannelTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.GroupMsg;
import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEventCard;
import com.norm.timemall.app.base.pojo.DefaultTextMessage;
import com.norm.timemall.app.ms.mapper.MsGroupMsgMapper;
import com.norm.timemall.app.ms.service.MsGroupMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MsGroupMessageServiceImpl implements MsGroupMessageService {
    @Autowired
    private MsGroupMsgMapper msGroupMsgMapper;


    @Override
    public void storeTextMessage(String channel, MsStoreDefaultTextMessageDTO dto) {

        DefaultTextMessage textMessage = new DefaultTextMessage();
        textMessage.setContent(dto.getMsg());
        Gson gson = new Gson();
        GroupMsg msg =new GroupMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setChannelId(channel)
                .setChannelType(ChannelTypeEnum.DEFAULT.getMark())
                .setAuthorId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setMsgType(dto.getMsgType())
                .setMsg(gson.toJson(textMessage))
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msGroupMsgMapper.insert(msg);

    }

    @Override
    public void storeImageMessage(String channel, String msgJson, String msgType) {
        GroupMsg msg =new GroupMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setChannelId(channel)
                .setChannelType(ChannelTypeEnum.DEFAULT.getMark())
                .setAuthorId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msGroupMsgMapper.insert(msg);
    }

    @Override
    public void storeAttachmentMessage(String channel, String msgJson, String msgType) {
        GroupMsg msg =new GroupMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setChannelId(channel)
                .setChannelType(ChannelTypeEnum.DEFAULT.getMark())
                .setAuthorId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msGroupMsgMapper.insert(msg);
    }

    @Override
    public void removeOneMessage(String channel, String messageId) {

        LambdaQueryWrapper<GroupMsg> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(GroupMsg::getMsgId,messageId)
                        .eq(GroupMsg::getChannelId,channel)
                                .eq(GroupMsg::getChannelType,ChannelTypeEnum.DEFAULT.getMark());
        msGroupMsgMapper.delete(wrapper);

    }

    @Override
    public IPage<MsDefaultEventCard> findEventPage(String channel, PageDTO dto) {

        IPage<MsDefaultEventCard> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return msGroupMsgMapper.selectEventPage(page,channel,ChannelTypeEnum.DEFAULT.getMark());

    }


}
