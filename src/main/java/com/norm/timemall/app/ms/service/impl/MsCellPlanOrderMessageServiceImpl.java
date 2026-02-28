package com.norm.timemall.app.ms.service.impl;

import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.mo.CellPlanMsg;
import com.norm.timemall.app.ms.controller.MsStoreMpsTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import com.norm.timemall.app.base.pojo.DefaultTextMessage;
import com.norm.timemall.app.ms.mapper.MsCellPlanMsgMapper;
import com.norm.timemall.app.ms.service.MsCellPlanOrderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MsCellPlanOrderMessageServiceImpl implements MsCellPlanOrderMessageService {
    @Autowired
    private MsCellPlanMsgMapper msCellPlanMsgMapper;
    @Override
    public MsDefaultEvent findCellPlanOrderEvent(String room) {
        return msCellPlanMsgMapper.selectEventByOrderId(room);
    }

    @Override
    public void addMessage(String room, MsStoreMpsTextMessageDTO dto) {
        DefaultTextMessage textMessage = new DefaultTextMessage();
        textMessage.setContent(dto.getMsg());
        Gson gson = new Gson();
        CellPlanMsg msg = new CellPlanMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setPlanOrderId(room)
                .setAuthorId(dto.getAuthorId())
                .setMsgType(dto.getMsgType())
                .setMsg(gson.toJson(textMessage))
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msCellPlanMsgMapper.insert(msg);
    }

    @Override
    public void addImageMessage(String room, String msgJson, String authorId, String msgType) {
        CellPlanMsg msg = new CellPlanMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setPlanOrderId(room)
                .setAuthorId(authorId)
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msCellPlanMsgMapper.insert(msg);
    }

    @Override
    public void addAttachmentMessage(String room, String msgJson, String authorId, String msgType) {
        CellPlanMsg msg = new CellPlanMsg();
        msg.setMsgId(IdUtil.simpleUUID())
                .setPlanOrderId(room)
                .setAuthorId(authorId)
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msCellPlanMsgMapper.insert(msg);
    }
}
