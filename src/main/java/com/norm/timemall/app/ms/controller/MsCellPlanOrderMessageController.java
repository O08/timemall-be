package com.norm.timemall.app.ms.controller;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultEvent;
import com.norm.timemall.app.ms.domain.pojo.MsDefaultFileMessage;
import com.norm.timemall.app.ms.domain.vo.MsDefaultEventVO;
import com.norm.timemall.app.ms.service.MsCellPlanOrderMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class MsCellPlanOrderMessageController {
    @Autowired
    private MsCellPlanOrderMessageService msCellPlanOrderMessageService;
    @Autowired
    private FileStoreService fileStoreService;
    @ResponseBody
    @GetMapping(value = "/api/v1/ms/plan_order/{room}/event")
    public MsDefaultEventVO retrieveCellPlanOrderEvent(@PathVariable("room") String room){
        MsDefaultEvent event = msCellPlanOrderMessageService.findCellPlanOrderEvent(room);
        MsDefaultEventVO vo = new MsDefaultEventVO();
        vo.setEvent(event==null? new MsDefaultEvent(): event);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @ResponseBody
    @PutMapping(value = "/api/v1/ms/plan_order/{room}/storeText")
    public SuccessVO storeMpsTextMessage(@PathVariable("room") String room, @RequestBody @Validated MsStoreMpsTextMessageDTO dto){
        msCellPlanOrderMessageService.addMessage(room,dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @ResponseBody
    @PutMapping(value = "/api/v1/ms/plan_order/{room}/storeImage")
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
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.DEFAULT_IMAGE_MESSAGE);
        MsDefaultFileMessage msg = new MsDefaultFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msCellPlanOrderMessageService.addImageMessage(room,gson.toJson(msg),authorId,msgType);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @ResponseBody
    @PutMapping(value = "/api/v1/ms/plan_order/{room}/storeAttachment")
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
        String uri = fileStoreService.storeWithUnlimitedAccess(file, FileStoreDir.DEFAULT_ATTACHMENT_MESSAGE);
        MsDefaultFileMessage msg = new MsDefaultFileMessage();
        msg.setUri(uri);
        msg.setFileName(file.getOriginalFilename());
        Gson gson = new Gson();
        msCellPlanOrderMessageService.addAttachmentMessage(room,gson.toJson(msg),authorId,msgType);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
