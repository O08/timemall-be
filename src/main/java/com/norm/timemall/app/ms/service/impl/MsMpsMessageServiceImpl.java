package com.norm.timemall.app.ms.service.impl;

import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.mo.MpsMsg;
import com.norm.timemall.app.ms.controller.MsStoreMpsTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultTextMessage;
import com.norm.timemall.app.ms.mapper.MsMpsMsgMapper;
import com.norm.timemall.app.ms.service.MsMpsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MsMpsMessageServiceImpl implements MsMpsMessageService {
    @Autowired
    private MsMpsMsgMapper msMpsMsgMapper;

    @Override
    public void addAttachmentMessage(String room, String msgJson, String authorId, String msgType) {
        MpsMsg msg = new MpsMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setTargetId(room)
                .setAuthorId(authorId)
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msMpsMsgMapper.insert(msg);
    }

    @Override
    public void addImageMessage(String room, String msgJson, String authorId, String msgType) {
        MpsMsg msg = new MpsMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setTargetId(room)
                .setAuthorId(authorId)
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msMpsMsgMapper.insert(msg);
    }

    @Override
    public void addMessage(String room, MsStoreMpsTextMessageDTO dto) {
        MsDefaultTextMessage textMessage = new MsDefaultTextMessage();
        textMessage.setContent(dto.getMsg());
        Gson gson = new Gson();
        MpsMsg msg = new MpsMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setTargetId(room)
                .setAuthorId(dto.getAuthorId())
                .setMsgType(dto.getMsgType())
                .setMsg(gson.toJson(textMessage))
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msMpsMsgMapper.insert(msg);
    }

    @Override
    public MsDefaultEvent findMillstoneEvent(String room) {
        return msMpsMsgMapper.selectMpsEventByTargetId(room);
    }

}
