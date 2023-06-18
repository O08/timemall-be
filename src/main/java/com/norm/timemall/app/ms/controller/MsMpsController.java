package com.norm.timemall.app.ms.controller;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.domain.pojo.MsMpsEvent;
import com.norm.timemall.app.ms.domain.pojo.MsMpsFileMessage;
import com.norm.timemall.app.ms.domain.vo.MsMpsEventVO;
import com.norm.timemall.app.ms.service.MsMpsMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
public class MsMpsController {
    @Autowired
    private MsMpsMessageService msMpsMessageService;

    @Autowired
    private FileStoreService fileStoreService;

    @ResponseBody
    @GetMapping(value = "/api/v1/ms/mps/{room}/event")
    public MsMpsEventVO retrieveMillstoneEvent(@PathVariable("room") String room){
        MsMpsEvent event = msMpsMessageService.findMillstoneEvent(room);
        MsMpsEventVO vo = new MsMpsEventVO();
        vo.setEvent(event==null? new MsMpsEvent(): event);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @ResponseBody
    @PutMapping(value = "/api/v1/ms/mps/{room}/storeText")
    public SuccessVO storeMpsTextMessage(@PathVariable("room") String room,@RequestBody @Validated MsStoreMpsTextMessageDTO dto){
        msMpsMessageService.addMessage(room,dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @ResponseBody
    @PutMapping(value = "/api/v1/ms/mps/{room}/storeImage")
    public SuccessVO storeMpsImageMessage(@PathVariable("room") String room,
                                                @RequestParam("file") MultipartFile file,
                                                @RequestParam("authorId") String authorId,
                                                @RequestParam("msgType") String msgType
    ){
        // validate
        if(file.isEmpty() || StrUtil.isBlank(authorId)|| StrUtil.isBlank(msgType)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.MPS_IMAGE_MESSAGE);
        MsMpsFileMessage msg = new MsMpsFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msMpsMessageService.addImageMessage(room,gson.toJson(msg),authorId,msgType);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/ms/mps/{room}/storeAttachment")
    public SuccessVO storeMpsAttachmentMessage(@PathVariable("room") String room,
                                                     @RequestParam(value = "file")  MultipartFile file,
                                                    @RequestParam(value = "authorId") String authorId,
                                                    @RequestParam(value = "msgType") String msgType
    ){
        // validate
        if(file.isEmpty() || StrUtil.isBlank(authorId)|| StrUtil.isBlank(msgType)){
          throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.MPS_ATTACHMENT_MESSAGE);
        MsMpsFileMessage msg = new MsMpsFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msMpsMessageService.addAttachmentMessage(room,gson.toJson(msg),authorId,msgType);
        return new SuccessVO(CodeEnum.SUCCESS);
    }





}
