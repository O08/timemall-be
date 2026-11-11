package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.enums.MsgTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.AppGroupChatMsg;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.base.pojo.DefaultFileMessage;
import com.norm.timemall.app.team.domain.dto.TeamAppGroupChatStoreTextMessageDTO;
import com.norm.timemall.app.team.domain.pojo.TeamAppGroupChatFetchMember;
import com.norm.timemall.app.team.domain.ro.TeamAppGroupChatFeedPageRO;
import com.norm.timemall.app.team.domain.vo.TeamAppGroupChatFeedPageVO;
import com.norm.timemall.app.team.domain.vo.TeamAppGroupChatFetchMemberVO;
import com.norm.timemall.app.team.service.TeamAppGroupChatService;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class TeamAppGroupChatController {

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;
    @Autowired
    private TeamAppGroupChatService teamAppGroupChatService;

    @Autowired
    private FileStoreService fileStoreService;


    @GetMapping(value = "/api/v1/app/group_chat/{channel}/feed")
    public TeamAppGroupChatFeedPageVO retrieveFeed(@PathVariable("channel") String channel, @Validated PageDTO dto ){

        IPage<TeamAppGroupChatFeedPageRO> event = teamAppGroupChatService.findFeeds(channel,dto);
        TeamAppGroupChatFeedPageVO vo = new TeamAppGroupChatFeedPageVO();
        vo.setEvent(event);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @GetMapping("/api/v1/app/group_chat/channel/{id}/member")
    public TeamAppGroupChatFetchMemberVO retrieveMember(@PathVariable("id") String id,String q){

        TeamAppGroupChatFetchMember member = teamAppGroupChatService.findMember(id,q);
        TeamAppGroupChatFetchMemberVO vo = new TeamAppGroupChatFetchMemberVO();
        vo.setMember(member);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PutMapping(value = "/api/v1/app/group_chat/feed/storeText")
    public SuccessVO storeTextMessage(@Validated  @RequestBody TeamAppGroupChatStoreTextMessageDTO dto){

        if(!MsgTypeEnum.TEXT.getMark().equals(dto.getMsgType())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only member can post
        boolean availablePublishFeed = teamDataPolicyService.alreadyOasisMember(dto.getChannel());
        if(!availablePublishFeed){
            throw new ErrorCodeException(CodeEnum.ONLY_FOR_MEMBER);
        }

        teamAppGroupChatService.doStoreTextMessage(dto);

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping(value = "/api/v1/app/group_chat/feed/storeImage")
    public SuccessVO storeImageMessage(@RequestParam("channel") String channel,
                                       @RequestParam("file") MultipartFile file,
                                       @RequestParam("msgType") String msgType) throws IOException {
        // validate
        if(file.isEmpty() || StrUtil.isBlank(msgType) || !MsgTypeEnum.IMAGE.getMark().equals(msgType)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String fileType= FileTypeUtil.getType(file.getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only member can post
        boolean availablePublishFeed = teamDataPolicyService.alreadyOasisMember(channel);
        if(!availablePublishFeed){
            throw new ErrorCodeException(CodeEnum.ONLY_FOR_MEMBER);
        }

        // store file
        String uri = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(file, FileStoreDir.DEFAULT_IMAGE_MESSAGE);
        DefaultFileMessage msg = new DefaultFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();

        teamAppGroupChatService.doStoreImageMessage(channel,gson.toJson(msg),msgType);

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping(value = "/api/v1/app/group_chat/feed/storeAttachment")
    public SuccessVO storeAttachment(
            @RequestParam("channel") String channel,
            @RequestParam(value = "file")  MultipartFile file,
            @RequestParam(value = "msgType") String msgType
    ){
        // validate
        if(file.isEmpty() ||  StrUtil.isBlank(msgType) || !MsgTypeEnum.ATTACHMENT.getMark().equals(msgType)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // only member can post
        boolean availablePublishFeed = teamDataPolicyService.alreadyOasisMember(channel);
        if(!availablePublishFeed){
            throw new ErrorCodeException(CodeEnum.ONLY_FOR_MEMBER);
        }

        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.DEFAULT_ATTACHMENT_MESSAGE);
        DefaultFileMessage msg = new DefaultFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();

        teamAppGroupChatService.doStoreAttachmentMessage(channel,gson.toJson(msg),msgType);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @DeleteMapping("/api/v1/app/group_chat/feed/{id}/del")
    public SuccessVO removeMessage(@PathVariable("id") String id){

        // find raw data
        AppGroupChatMsg msg = teamAppGroupChatService.findOneFeed(id);
        if(msg==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // only member can del
        boolean availablePublishFeed = teamDataPolicyService.alreadyOasisMember(msg.getOasisChannelId());
        if(!availablePublishFeed){
            throw new ErrorCodeException(CodeEnum.ONLY_FOR_MEMBER);
        }

        Gson gson = new Gson();

        // delete from oss
        teamAppGroupChatService.doRemoveMessage(id);

        // if image message ,delete from oss
        if(MsgTypeEnum.IMAGE.getMark().equals(msg.getMsgType())){
            fileStoreService.deleteImageAndAvifFile(gson.fromJson(msg.getMsg(), DefaultFileMessage.class).getUri());
        }

        // if file message ,delete from oss
        if(MsgTypeEnum.ATTACHMENT.getMark().equals(msg.getMsgType())){
            fileStoreService.deleteFile(gson.fromJson(msg.getMsg(), DefaultFileMessage.class).getUri());
        }


        return new SuccessVO(CodeEnum.SUCCESS);

    }




}
