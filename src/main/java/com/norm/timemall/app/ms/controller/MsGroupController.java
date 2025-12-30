package com.norm.timemall.app.ms.controller;

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
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEventCard;
import com.norm.timemall.app.base.pojo.DefaultFileMessage;
import com.norm.timemall.app.ms.domain.pojo.MsFetchGroupMemberProfile;
import com.norm.timemall.app.ms.domain.vo.MsDefaultEventPageVO;
import com.norm.timemall.app.ms.domain.vo.MsFetchGroupMemberProfileVO;
import com.norm.timemall.app.ms.service.MsGroupMemberRelService;
import com.norm.timemall.app.ms.service.MsGroupMessageService;
import com.norm.timemall.app.ms.service.MsGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class MsGroupController {
    @Autowired
    private MsGroupMessageService msGroupMessageService;
    @Autowired
    private MsGroupMemberRelService msGroupMemberRelService;
    @Autowired
    private MsGroupService msGroupService;

    @Autowired
    private FileStoreService fileStoreService;
    @ResponseBody
    @GetMapping(value = "/api/v1/ms/group/{channel}/event")
    public MsDefaultEventPageVO retrieveGroupEvent(@PathVariable("channel") String channel, @Validated PageDTO dto ){

        boolean beMember= msGroupMemberRelService.beGroupMember(channel);
        if(!beMember){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        IPage<MsDefaultEventCard> event = msGroupMessageService.findEventPage(channel,dto);
        MsDefaultEventPageVO vo  = new MsDefaultEventPageVO();
        vo.setEvent(event);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @ResponseBody
    @PutMapping(value = "/api/v1/ms/group/{channel}/storeText")
    public SuccessVO storeGroupTextMessage(@PathVariable("channel") String channel, @RequestBody @Validated MsStoreDefaultTextMessageDTO dto){

        boolean enableRW = msGroupMemberRelService.haveReadAndWriteForOasis(channel);
        if(!enableRW || !MsgTypeEnum.TEXT.getMark().equals(dto.getMsgType())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        msGroupMessageService.storeTextMessage(channel,dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @ResponseBody
    @PutMapping(value = "/api/v1/ms/group/{channel}/storeImage")
    public SuccessVO storeGroupImageMessage(@PathVariable("channel") String channel,
                                                @RequestParam("file") MultipartFile file,
                                                @RequestParam("msgType") String msgType
    ) throws IOException {

        // validate
        if(file.isEmpty() || StrUtil.isBlank(msgType) || !MsgTypeEnum.IMAGE.getMark().equals(msgType)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String fileType=FileTypeUtil.getType(file.getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        boolean enableRW = msGroupMemberRelService.haveReadAndWriteForOasis(channel);
        if(!enableRW){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.DEFAULT_IMAGE_MESSAGE);
        DefaultFileMessage msg = new DefaultFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msGroupMessageService.storeImageMessage(channel,gson.toJson(msg),msgType);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @ResponseBody
    @PutMapping(value = "/api/v1/ms/group/{channel}/storeAttachment")
    public SuccessVO storeGroupAttachmentMessage(@PathVariable("channel") String channel,
                                               @RequestParam(value = "file")  MultipartFile file,
                                               @RequestParam(value = "msgType") String msgType
    ){

        // validate
        if(file.isEmpty() ||  StrUtil.isBlank(msgType) || !MsgTypeEnum.ATTACHMENT.getMark().equals(msgType)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        boolean enableRW = msGroupMemberRelService.haveReadAndWriteForOasis(channel);
        if(!enableRW){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.DEFAULT_ATTACHMENT_MESSAGE);
        DefaultFileMessage msg = new DefaultFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msGroupMessageService.storeAttachmentMessage(channel,gson.toJson(msg),msgType);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @ResponseBody
    @DeleteMapping("/api/v1/ms/group/{channel}/event/{message_id}/del")
    public SuccessVO delOneMessage(@PathVariable("channel") String channel,@PathVariable("message_id") String messageId){
       boolean beAdmin= msGroupService.beAdmin(channel);
       if(!beAdmin){
           throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
       }
        msGroupMessageService.removeOneMessage(channel,messageId);

        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @ResponseBody
    @PutMapping("/api/v1/ms/group/{channel}/member/{user_id}/ban")
    public SuccessVO banUser(@PathVariable("channel") String channel,@PathVariable("user_id") String userId){

        boolean beAdmin= msGroupService.beAdmin(channel);
        boolean beAdminUserId= SecurityUserHelper.getCurrentPrincipal().getUserId().equals(userId);
        if(!beAdmin || beAdminUserId){
            throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        msGroupMemberRelService.banOneUser(channel,userId);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @ResponseBody
    @GetMapping(value = "/api/v1/ms/group/{channel}/member/{id}/profile")
    public MsFetchGroupMemberProfileVO fetchGroupMemberProfile(@PathVariable("channel") String channel, @PathVariable("id") String memberUserId){

        MsFetchGroupMemberProfile profile = msGroupMemberRelService.findOneMemberProfile(channel,memberUserId);
        MsFetchGroupMemberProfileVO vo = new MsFetchGroupMemberProfileVO();
        vo.setProfile(profile);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;

    }


}
