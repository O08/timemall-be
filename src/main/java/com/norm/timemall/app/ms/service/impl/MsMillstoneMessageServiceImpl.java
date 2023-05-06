package com.norm.timemall.app.ms.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.mo.MillstoneMsg;
import com.norm.timemall.app.ms.domain.dto.MsStoreMillstoneMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsMillstoneEvent;
import com.norm.timemall.app.ms.mapper.MsMillstoneMsgMapper;
import com.norm.timemall.app.ms.service.MsMillstoneMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MsMillstoneMessageServiceImpl implements MsMillstoneMessageService {
    @Autowired
    private MsMillstoneMsgMapper msMillstoneMsgMapper;
    @Override
    public MsMillstoneEvent findMillstoneEvent(String millstoneId) {
        return msMillstoneMsgMapper.selectMillstoneEventByMillstoneId(millstoneId);
    }

    @Override
    public void addMessage(String millstoneId, MsStoreMillstoneMessageDTO dto) {
        MillstoneMsg millstoneMsg = new MillstoneMsg();
        millstoneMsg.setMsgId(IdUtil.simpleUUID())
                .setMillstoneId(millstoneId)
                .setAuthorId(dto.getAuthorId())
                .setMsgType(dto.getMsgType())
                .setMsg(dto.getMsg())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msMillstoneMsgMapper.insert(millstoneMsg);
    }
}
