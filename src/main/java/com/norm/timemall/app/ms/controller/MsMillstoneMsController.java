package com.norm.timemall.app.ms.controller;

import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.domain.dto.MsStoreMillstoneMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsMillstoneFileMessage;
import com.norm.timemall.app.ms.domain.pojo.MsMillstoneEvent;
import com.norm.timemall.app.ms.domain.vo.MsMillstoneEventVO;
import com.norm.timemall.app.ms.service.MsMillstoneMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MsMillstoneMsController {
    @Autowired
    private MsMillstoneMessageService msMillstoneMessageService;
    @Autowired
    private FileStoreService fileStoreService;

    @ResponseBody
    @GetMapping(value = "/api/v1/ms/millstone/{millstone_id}/event")
    public MsMillstoneEventVO retrieveMillstoneEvent(@PathVariable("millstone_id") String millstoneId){
        MsMillstoneEvent event = msMillstoneMessageService.findMillstoneEvent(millstoneId);
        MsMillstoneEventVO vo =new MsMillstoneEventVO();
        vo.setEvent(event==null? new MsMillstoneEvent(): event);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/ms/millstone/{millstone_id}/store")
    public SuccessVO storeMillstoneMessage(@PathVariable("millstone_id") String millstoneId,
                                           @RequestBody MsStoreMillstoneMessageDTO dto){
        msMillstoneMessageService.addMessage(millstoneId,dto);

        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @ResponseBody
    @PutMapping(value = "/api/v1/ms/millstone/{millstone_id}/storeImage")
    public SuccessVO storeMillstoneImageMessage(@PathVariable("millstone_id") String millstoneId,
                                                @RequestParam("file") MultipartFile file,
                                                @RequestParam("authorId") String authorId,
                                                @RequestParam("msgType") String msgType
                                                ){
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.MILLSTONE_IMAGE_MESSAGE);
        MsMillstoneFileMessage msg = new MsMillstoneFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msMillstoneMessageService.addImageMessage(millstoneId,gson.toJson(msg),authorId,msgType);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @ResponseBody
    @PutMapping(value = "/api/v1/ms/millstone/{millstone_id}/storeAttachment")
    public SuccessVO storeMillstoneAttachmentMessage(@PathVariable("millstone_id") String millstoneId,
                                                @RequestParam("file") MultipartFile file,
                                                @RequestParam("authorId") String authorId,
                                                @RequestParam("msgType") String msgType
    ){
        // store file
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.MILLSTONE_ATTACHMENT_MESSAGE);
        MsMillstoneFileMessage msg = new MsMillstoneFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msMillstoneMessageService.addAttachmentMessage(millstoneId,gson.toJson(msg),authorId,msgType);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
