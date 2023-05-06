package com.norm.timemall.app.ms.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.ms.domain.dto.MsStoreMillstoneMessageDTO;
import com.norm.timemall.app.ms.domain.pojo.MsMillstoneEvent;
import com.norm.timemall.app.ms.domain.vo.MsMillstoneEventVO;
import com.norm.timemall.app.ms.service.MsMillstoneMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class MsMillstoneMsController {
    @Autowired
    private MsMillstoneMessageService msMillstoneMessageService;

    @ResponseBody
    @GetMapping(value = "/api/v1/ms/millstone/{millstone_id}/event")
    public MsMillstoneEventVO retrieveMillstoneEvent(@PathVariable("millstone_id") String millstoneId){
        MsMillstoneEvent event = msMillstoneMessageService.findMillstoneEvent(millstoneId);
        MsMillstoneEventVO vo =new MsMillstoneEventVO();
        vo.setEvent(event);
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
}
