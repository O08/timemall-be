package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.FetchCellCouponBenefitDTO;
import com.norm.timemall.app.studio.domain.dto.StudioRefreshPromotionDTO;
import com.norm.timemall.app.studio.domain.ro.FetchBrandPromotionRO;
import com.norm.timemall.app.studio.domain.ro.FetchCellCouponBenefitRO;
import com.norm.timemall.app.studio.domain.vo.FetchBrandPromotionVO;
import com.norm.timemall.app.studio.domain.vo.FetchCellCouponBenefitVO;
import com.norm.timemall.app.studio.service.StudioBrandPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioPromotionController {

    @Autowired
    private StudioBrandPromotionService studioBrandPromotionService;
    @GetMapping(value = "/api/v1/web_estudio/brand/promotion")
    public FetchBrandPromotionVO fetchBrandPromotionInfo(){

        FetchBrandPromotionVO vo = new FetchBrandPromotionVO();
        FetchBrandPromotionRO ro = studioBrandPromotionService.findBrandPromotion();
        vo.setPromotion(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PostMapping("/api/v1/web_estudio/brand/init_promotion")
    public SuccessVO setUpPromotion(){
        studioBrandPromotionService.setupBrandPromotionInfo();
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/web_estudio/brand/modify_promotion")
    public SuccessVO refreshPromotionInfo(@Validated @RequestBody StudioRefreshPromotionDTO dto){
        studioBrandPromotionService.modifyPromotion(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/web_estudio/coupon_benefit")
    public FetchCellCouponBenefitVO fetchCellCouponBenefit(@Validated  FetchCellCouponBenefitDTO dto){

        FetchCellCouponBenefitRO ro = studioBrandPromotionService.findCouponBenefit(dto.getCellId(), dto.getSupplierBrandId());
        FetchCellCouponBenefitVO vo = new FetchCellCouponBenefitVO();
        vo.setBenefit(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

}
