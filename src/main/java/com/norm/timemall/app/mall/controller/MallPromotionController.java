package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.MallFetchPromotionInfoDTO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionInfoRO;
import com.norm.timemall.app.mall.domain.vo.MallFetchPromotionInfoVO;
import com.norm.timemall.app.mall.service.MallBrandPromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MallPromotionController {
    @Autowired
    private MallBrandPromotionService mallBrandPromotionService;
    @GetMapping("/api/v1/web_mall/cell/promotion")
    public MallFetchPromotionInfoVO fetchPromotionInfo(@Validated MallFetchPromotionInfoDTO dto){

        MallFetchPromotionInfoRO ro = mallBrandPromotionService.findPromotionInfo(dto);
        MallFetchPromotionInfoVO vo= new MallFetchPromotionInfoVO();
        vo.setPromotion(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
