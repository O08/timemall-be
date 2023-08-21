package com.norm.timemall.app.ms.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.enums.MsgTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultFileMessage;
import com.norm.timemall.app.ms.domain.vo.MsDefaultEventVO;
import com.norm.timemall.app.ms.service.MsPrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class MsPrivateController {
    @Autowired
    private MsPrivateMessageService msPrivateMessageService;
    @Autowired
    private FileStoreService fileStoreService;
    @Autowired
    private AccountService accountService;
    @ResponseBody
    @GetMapping(value = "/api/v1/ms/private/{friend}/event")
    public MsDefaultEventVO retrievePrivateEvent(@PathVariable("friend") String friend){

        MsDefaultEvent event = msPrivateMessageService.findEvent(friend);

        MsDefaultEventVO vo = new MsDefaultEventVO();
        vo.setEvent(event==null? new MsDefaultEvent(): event);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @ResponseBody
    @PutMapping(value = "/api/v1/ms/private/{friend}/event/storeText")
    public SuccessVO storePrivateTextMessage(@PathVariable("friend") String friend, @RequestBody  @Validated MsStoreDefaultTextMessageDTO dto){
        Brand friendBrand = accountService.findBrandInfoByUserId(friend);
        if(friendBrand==null || !MsgTypeEnum.TEXT.getMark().equals(dto.getMsgType())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        msPrivateMessageService.storeTextMessage(friend,dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @ResponseBody
    @PutMapping(value = "/api/v1/ms/private/{friend}/event/storeImage")
    public SuccessVO storePrivateImageMessage(@PathVariable("friend") String friend,
                                            @RequestParam("file") MultipartFile file,
                                            @RequestParam("msgType") String msgType
    ) throws IOException {

        // validate
        if(file.isEmpty() ||  StrUtil.isBlank(msgType) || !MsgTypeEnum.IMAGE.getMark().equals(msgType)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String fileType= FileTypeUtil.getType(file.getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        Brand friendBrand = accountService.findBrandInfoByUserId(friend);
        if(friendBrand==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.DEFAULT_IMAGE_MESSAGE);
        MsDefaultFileMessage msg = new MsDefaultFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msPrivateMessageService.storeImageMessage(friend,gson.toJson(msg),msgType);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @ResponseBody
    @PutMapping(value = "/api/v1/ms/private/{friend}/event/storeAttachment")
    public SuccessVO storePrivateAttachmentMessage(@PathVariable("friend") String friend,
                                                 @RequestParam(value = "file")  MultipartFile file,
                                                 @RequestParam(value = "msgType") String msgType
    ){

        // validate
        if(file.isEmpty() ||  StrUtil.isBlank(msgType) || !MsgTypeEnum.ATTACHMENT.getMark().equals(msgType)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        Brand friendBrand = accountService.findBrandInfoByUserId(friend);
        if(friendBrand==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.DEFAULT_ATTACHMENT_MESSAGE);
        MsDefaultFileMessage msg = new MsDefaultFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msPrivateMessageService.storeAttachmentMessage(friend,gson.toJson(msg),msgType);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @ResponseBody
    @DeleteMapping("/api/v1/ms/private/{friend}/event/remove_all")
    public SuccessVO delAllMessageForOneFriend(@PathVariable("friend") String friend){

        msPrivateMessageService.removeAllMessageForOneFriend(friend);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @ResponseBody
    @DeleteMapping("/api/v1/ms/private/me/event/{message_id}/recall_one")
    public SuccessVO recallOneMessage(@PathVariable("message_id") String messageId){

        msPrivateMessageService.recallOneMessage(messageId);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @ResponseBody
    @DeleteMapping("/api/v1/ms/private/me/event/{message_id}/remove_one")
    public SuccessVO removeOneMessage(@PathVariable("message_id") String messageId){

        msPrivateMessageService.removeOneMessage(messageId);
        return new SuccessVO(CodeEnum.SUCCESS);

    }




}
