package com.norm.timemall.app.ms.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.PrivateMsg;
import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultTextMessage;
import com.norm.timemall.app.ms.mapper.MsPrivateMsgMapper;
import com.norm.timemall.app.ms.service.MsPrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MsPrivateMessageServiceImpl implements MsPrivateMessageService {
    @Autowired
    private MsPrivateMsgMapper msPrivateMsgMapper;
    @Override
    public MsDefaultEvent findEvent(String friend) {
        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        return msPrivateMsgMapper.selectEventByFromIdAndToId(friend,currentUserId);
    }

    @Override
    public void storeTextMessage(String friend, MsStoreDefaultTextMessageDTO dto) {

        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        String msgNo=IdUtil.simpleUUID();
        MsDefaultTextMessage textMessage = new MsDefaultTextMessage();
        textMessage.setContent(dto.getMsg());
        Gson gson = new Gson();
        PrivateMsg meMsgRecord = new PrivateMsg();
        meMsgRecord.setMsgId(IdUtil.simpleUUID())
                .setToId(friend)
                .setFromId(currentUserId)
                .setMsgType(dto.getMsgType())
                .setMsg(gson.toJson(textMessage))
                .setOwnerUserId(currentUserId)
                .setMsgNo(msgNo)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        PrivateMsg friendMsgRecord = new PrivateMsg();
        friendMsgRecord.setMsgId(IdUtil.simpleUUID())
                .setToId(friend)
                .setFromId(currentUserId)
                .setMsgType(dto.getMsgType())
                .setMsg(gson.toJson(textMessage))
                .setOwnerUserId(friend)
                .setMsgNo(msgNo)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        List<PrivateMsg> privateMsgList =  new ArrayList<>();
        privateMsgList.add(meMsgRecord);
        privateMsgList.add(friendMsgRecord);
        msPrivateMsgMapper.insertBatchSomeColumn(privateMsgList);

    }

    @Override
    public void storeImageMessage(String friend, String msgJson,  String msgType) {

        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        String msgNo=IdUtil.simpleUUID();
        PrivateMsg meMsgRecord = new PrivateMsg();
        meMsgRecord.setMsgId(IdUtil.simpleUUID())
                .setToId(friend)
                .setFromId(currentUserId)
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setOwnerUserId(currentUserId)
                .setMsgNo(msgNo)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        PrivateMsg friendMsgRecord = new PrivateMsg();
        friendMsgRecord.setMsgId(IdUtil.simpleUUID())
                .setToId(friend)
                .setFromId(currentUserId)
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setOwnerUserId(friend)
                .setMsgNo(msgNo)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        List<PrivateMsg> privateMsgList =  new ArrayList<>();
        privateMsgList.add(meMsgRecord);
        privateMsgList.add(friendMsgRecord);
        msPrivateMsgMapper.insertBatchSomeColumn(privateMsgList);

    }

    @Override
    public void storeAttachmentMessage(String friend, String msgJson, String msgType) {

        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        String msgNo=IdUtil.simpleUUID();
        PrivateMsg meMsgRecord = new PrivateMsg();
        meMsgRecord.setMsgId(IdUtil.simpleUUID())
                .setToId(friend)
                .setFromId(currentUserId)
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setOwnerUserId(currentUserId)
                .setMsgNo(msgNo)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        PrivateMsg friendMsgRecord = new PrivateMsg();
        friendMsgRecord.setMsgId(IdUtil.simpleUUID())
                .setToId(friend)
                .setFromId(currentUserId)
                .setMsgType(msgType)
                .setMsg(msgJson)
                .setOwnerUserId(friend)
                .setMsgNo(msgNo)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        List<PrivateMsg> privateMsgList =  new ArrayList<>();
        privateMsgList.add(meMsgRecord);
        privateMsgList.add(friendMsgRecord);
        msPrivateMsgMapper.insertBatchSomeColumn(privateMsgList);

    }

    @Override
    public void removeAllMessageForOneFriend(String friend) {

        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        msPrivateMsgMapper.deleteByOwnerIdAndFromIdAndToId(friend,currentUserId);

    }

    @Override
    public void removeOneMessage(String messageId) {

        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        PrivateMsg toDeleteMsgOne = msPrivateMsgMapper.selectById(messageId);
        if(toDeleteMsgOne==null || !toDeleteMsgOne.getOwnerUserId().equals(currentUserId)){
            throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        msPrivateMsgMapper.deleteById(messageId);

    }

    @Override
    public void recallOneMessage(String messageId) {

        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        PrivateMsg toDeleteMsgOne = msPrivateMsgMapper.selectById(messageId);
        boolean denyRecall = toDeleteMsgOne==null || !toDeleteMsgOne.getFromId().equals(currentUserId)
                || DateUtil.between(toDeleteMsgOne.getCreateAt(), new Date(), DateUnit.SECOND) > 60;
        if(denyRecall){
            throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        LambdaQueryWrapper<PrivateMsg> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(PrivateMsg::getMsgNo,toDeleteMsgOne.getMsgNo());
        msPrivateMsgMapper.delete(wrapper);

    }
}
