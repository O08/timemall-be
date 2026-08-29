package com.norm.timemall.app.ms.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.MsgTypeEnum;
import com.norm.timemall.app.base.enums.SseEventBusSceneEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.PrivateMsg;
import com.norm.timemall.app.base.mo.PrivateRel;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEventCard;
import com.norm.timemall.app.base.pojo.DefaultFileMessage;
import com.norm.timemall.app.base.pojo.DefaultTextMessage;
import com.norm.timemall.app.ms.domain.pojo.SseEventMessage;
import com.norm.timemall.app.ms.helper.SseHelper;
import com.norm.timemall.app.ms.mapper.MsPrivateMsgMapper;
import com.norm.timemall.app.ms.mapper.MsPrivateRelMapper;
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
    @Autowired
    private MsPrivateRelMapper msPrivateRelMapper;

    @Autowired
    private FileStoreService fileStoreService;
    @Override
    public void storeTextMessage(String friend, MsStoreDefaultTextMessageDTO dto) {

        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        String msgNo=IdUtil.simpleUUID();
        DefaultTextMessage textMessage = new DefaultTextMessage();
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
        // friend will be msg receiver, currentUser will be the friend of receiver
        String latestContent=getPrivateRelLatestContentWhenSendMsg(MsgTypeEnum.TEXT,dto.getMsg());
        ssePushOneMessageHandler(friend,currentUserId,latestContent);

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
        // friend will be msg receiver, currentUser will be the friend of receiver
        String latestContent=getPrivateRelLatestContentWhenSendMsg(MsgTypeEnum.IMAGE,"");
        ssePushOneMessageHandler(friend,currentUserId,latestContent);

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
        // friend will be msg receiver, currentUser will be the friend of receiver
        DefaultFileMessage msDefaultFileMessage = new Gson().fromJson(msgJson, DefaultFileMessage.class);
        String latestContent=getPrivateRelLatestContentWhenSendMsg(MsgTypeEnum.ATTACHMENT,msDefaultFileMessage.getFileName());
        ssePushOneMessageHandler(friend,currentUserId,latestContent);

    }

    @Override
    public void removeAllMessageForOneFriend(String friend) {

        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        msPrivateMsgMapper.deleteByOwnerIdAndFromIdAndToId(friend,currentUserId);

        LambdaQueryWrapper<PrivateRel> relDelWrapper = Wrappers.lambdaQuery();
        relDelWrapper.eq(PrivateRel::getUserId,currentUserId)
                        .eq(PrivateRel::getFriendId,friend);
        msPrivateRelMapper.delete(relDelWrapper);

    }

    @Override
    public void removeOneMessage(String messageId) {

        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        PrivateMsg toDeleteMsgOne = msPrivateMsgMapper.selectById(messageId);
        if(toDeleteMsgOne==null || !toDeleteMsgOne.getOwnerUserId().equals(currentUserId)){
            throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        msPrivateMsgMapper.deleteById(messageId);

        modifyPrivateRelLatestContent(currentUserId,toDeleteMsgOne.getToId(),"你移除了一条消息.");

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

        // delete image and file
        Gson gson =new Gson();
        if( MsgTypeEnum.IMAGE.getMark().equals(toDeleteMsgOne.getMsgType()) ||
            MsgTypeEnum.ATTACHMENT.getMark().equals(toDeleteMsgOne.getMsgType())){
            DefaultFileMessage msDefaultFileMessage = gson.fromJson(toDeleteMsgOne.getMsg(), DefaultFileMessage.class);
            fileStoreService.deleteFile(msDefaultFileMessage.getUri());
        }
        modifyPrivateRelLatestContent(currentUserId,toDeleteMsgOne.getToId(),"你撤回了一条消息.");
        modifyPrivateRelLatestContent(toDeleteMsgOne.getToId(),currentUserId,"对方撤回了一条消息.");


    }

    @Override
    public IPage<MsDefaultEventCard> findEventPage(String friend, PageDTO dto) {
        String currentUserId= SecurityUserHelper.getCurrentPrincipal().getUserId();


        IPage<MsDefaultEventCard> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        return msPrivateMsgMapper.selectEventPage(page,friend,currentUserId);

    }

    private void ssePushOneMessageHandler(String msgReceiver,String friendOfReceiver,String latestContent){

        LambdaQueryWrapper<PrivateRel> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(PrivateRel::getUserId,msgReceiver)
                        .eq(PrivateRel::getFriendId,friendOfReceiver);
        PrivateRel dbPrivateRel = msPrivateRelMapper.selectOne(wrapper);


        if(dbPrivateRel==null){
            // add friend rel records
            newPrivateRel(msgReceiver,friendOfReceiver,1L,latestContent);
        }
        if(ObjectUtil.isNotNull(dbPrivateRel)){
            dbPrivateRel.setUnread(dbPrivateRel.getUnread()+1L);
            dbPrivateRel.setModifiedAt(new Date());
            dbPrivateRel.setLatestContent(latestContent);
            msPrivateRelMapper.updateById(dbPrivateRel);
        }

        LambdaQueryWrapper<PrivateRel> currentUserRelWrapper= Wrappers.lambdaQuery();
        currentUserRelWrapper.eq(PrivateRel::getUserId,friendOfReceiver)
                .eq(PrivateRel::getFriendId,msgReceiver);
        PrivateRel currentUserDbPrivateRel = msPrivateRelMapper.selectOne(currentUserRelWrapper);
        if(currentUserDbPrivateRel==null){
            // add friend rel records
            newPrivateRel(friendOfReceiver,msgReceiver,0L,latestContent);
        }
        if(ObjectUtil.isNotNull(currentUserDbPrivateRel)){
            currentUserDbPrivateRel.setModifiedAt(new Date());
            currentUserDbPrivateRel.setLatestContent(latestContent);
            msPrivateRelMapper.updateById(currentUserDbPrivateRel);
        }

        doPushOneMessageToFriend(msgReceiver,friendOfReceiver);

    }

        private void newPrivateRel(String msgReceiver,String friendOfReceiver,Long unread,String latestContent){

        PrivateRel privateRel = new PrivateRel();
        privateRel.setId(IdUtil.simpleUUID())
                .setUserId(msgReceiver)
                .setFriendId(friendOfReceiver)
                .setUnread(unread)
                .setLatestContent(latestContent)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        msPrivateRelMapper.insert(privateRel);

    }
    private void doPushOneMessageToFriend(String msgReceiver,String from){

        SseEventMessage msg = new SseEventMessage();
        msg.setHandlerId(msgReceiver);
        msg.setScene(SseEventBusSceneEnum.PRIVATE.name());
        msg.setMsg("New");
        msg.setFrom(from);
        SseHelper.sendMessage(msgReceiver,new Gson().toJson(msg));

    }
    private String getPrivateRelLatestContentWhenSendMsg( MsgTypeEnum msgType, String remark){

        String latestContent;
        switch (msgType){
            case TEXT:
                latestContent=remark;
                break;
            case IMAGE:
                latestContent="[图片]";
                break;
            case ATTACHMENT:
                latestContent="[文件] " + remark;
                break;
            default:
                latestContent="未知内容";
        }
        return  latestContent;

    }

    private void modifyPrivateRelLatestContent(String friendOfReceiver,String msgReceiver, String latestContent){
        LambdaQueryWrapper<PrivateRel> currentUserRelWrapper= Wrappers.lambdaQuery();
        currentUserRelWrapper.eq(PrivateRel::getUserId,friendOfReceiver)
                .eq(PrivateRel::getFriendId,msgReceiver);

        msPrivateRelMapper.update(new PrivateRel().setLatestContent(latestContent).setModifiedAt(new Date()),
                currentUserRelWrapper);
    }
}
