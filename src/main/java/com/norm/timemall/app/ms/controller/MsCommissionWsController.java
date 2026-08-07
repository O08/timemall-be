package com.norm.timemall.app.ms.controller;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.enums.MsgTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.ms.domain.dto.MsStoreDefaultTextMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import com.norm.timemall.app.base.pojo.DefaultFileMessage;
import com.norm.timemall.app.ms.domain.vo.MsDefaultEventVO;
import com.norm.timemall.app.ms.service.MsCommissionMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;


@RestController
public class MsCommissionWsController {
    @Autowired
    private MsCommissionMessageService msCommissionMessageService;
    @Autowired
    private FileStoreService fileStoreService;
    @ResponseBody
    @GetMapping(value = "/api/v1/ms/commission_ws/{channel}/event")
    public MsDefaultEventVO retrieveCommissionWsEvent(@PathVariable("channel") String channel){

        MsDefaultEvent event = msCommissionMessageService.findEvent(channel);
        MsDefaultEventVO vo = new MsDefaultEventVO();
        vo.setEvent(event==null? new MsDefaultEvent(): event);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @ResponseBody
    @PutMapping(value = "/api/v1/ms/commission_ws/{channel}/storeText")
    public SuccessVO storeCommissionWsTextMessage(@PathVariable("channel") String channel, @RequestBody  @Validated MsStoreDefaultTextMessageDTO dto){
        boolean channelExist=msCommissionMessageService.channelExist(channel);
        if(!channelExist || !MsgTypeEnum.TEXT.getMark().equals(dto.getMsgType())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        msCommissionMessageService.storeTextMessage(channel,dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @ResponseBody
    @PutMapping(value = "/api/v1/ms/commission_ws/{channel}/storeImage")
    public SuccessVO storeCommissionWsImageMessage(@PathVariable("channel") String channel,
                                            @RequestParam("file") MultipartFile file,
                                            @RequestParam("msgType") String msgType
    ) throws IOException {

        // validate
        if(file.isEmpty() ||  StrUtil.isBlank(msgType)  || !MsgTypeEnum.IMAGE.getMark().equals(msgType)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String fileType= FileTypeUtil.getType(file.getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        boolean channelExist=msCommissionMessageService.channelExist(channel);
        if(!channelExist){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.DEFAULT_IMAGE_MESSAGE);
        DefaultFileMessage msg = new DefaultFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msCommissionMessageService.storeImageMessage(channel,gson.toJson(msg),msgType);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @ResponseBody
    @PutMapping(value = "/api/v1/ms/commission_ws/{channel}/storeAttachment")
    public SuccessVO storeCommissionWsAttachmentMessage(@PathVariable("channel") String channel,
                                                 @RequestParam(value = "file")  MultipartFile file,
                                                 @RequestParam(value = "msgType") String msgType
    ){

        // validate
        if(file.isEmpty() || StrUtil.isBlank(msgType)  || !MsgTypeEnum.ATTACHMENT.getMark().equals(msgType)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        boolean channelExist=msCommissionMessageService.channelExist(channel);
        if(!channelExist){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.DEFAULT_ATTACHMENT_MESSAGE);
        DefaultFileMessage msg = new DefaultFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msCommissionMessageService.storeAttachmentMessage(channel,gson.toJson(msg),msgType);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
