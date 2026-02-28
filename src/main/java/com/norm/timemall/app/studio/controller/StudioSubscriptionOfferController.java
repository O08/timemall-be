package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsOfferPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioGetOneSubsOfferVO;
import com.norm.timemall.app.studio.domain.vo.StudioGetSubsOfferPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioSubsGetShoppingOfferVO;
import com.norm.timemall.app.studio.service.StudioSubscriptionOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioSubscriptionOfferController {
    @Autowired
    private StudioSubscriptionOfferService studioSubscriptionOfferService;



    @GetMapping("/api/v1/web_estudio/brand/subscription/offer/query")
    public StudioGetSubsOfferPageVO getSubsOffer(@Validated StudioGetSubsOfferPageDTO dto){
        IPage<StudioGetSubsOfferPageRO> offer = studioSubscriptionOfferService.findOffer(dto);
        StudioGetSubsOfferPageVO vo = new StudioGetSubsOfferPageVO();
        vo.setOffer(offer);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @PostMapping("/api/v1/web_estudio/brand/subscription/offer/new")
    public SuccessVO createNewOffer(@Validated @RequestBody StudioCreateNewSubsOfferDTO dto){

        studioSubscriptionOfferService.newOffer(dto);
        return  new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_estudio/brand/subscription/offer/change")
    public SuccessVO changeOffer(@Validated @RequestBody StudioChangeSubsOfferDTO dto){

        studioSubscriptionOfferService.changeOneOffer(dto);
        return  new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_estudio/brand/subscription/offer/modify_status")
    public SuccessVO changeStatus(@Validated @RequestBody StudioSubsOfferChangeStatusDTO dto){
        studioSubscriptionOfferService.modifyStatus(dto);
        return  new SuccessVO(CodeEnum.SUCCESS);

    }
    @DeleteMapping("/api/v1/web_estudio/brand/subscription/offer/{id}/del")
    public SuccessVO delOneOffer(@PathVariable("id") String id){
        studioSubscriptionOfferService.removeOffer(id);
        return  new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping("/api/public/shopping/subscription/offer/query")
    public StudioSubsGetShoppingOfferVO getShoppingOffer(@Validated StudioSubsGetShoppingOfferDTO dto){
       return  studioSubscriptionOfferService.findOfferWhenShopping(dto);
    }


}
