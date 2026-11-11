package com.norm.timemall.app.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.MallFetchPhysicalProductPageDTO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPhysicalProductPageRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPhysicalProductInfoRO;
import com.norm.timemall.app.mall.domain.vo.MallFetchPhysicalProductInfoVO;
import com.norm.timemall.app.mall.domain.vo.MallFetchPhysicalProductPageVO;
import com.norm.timemall.app.mall.service.MallPhysicalProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MallPhysicalProductController {
    @Autowired
    private MallPhysicalProductService physicalProductService;

    @GetMapping(value = "/api/v1/web_mall/brand/physical_product")
    public MallFetchPhysicalProductPageVO fetchPhysicalProductPage(@Validated MallFetchPhysicalProductPageDTO dto){
        IPage<MallFetchPhysicalProductPageRO> product = physicalProductService.findBrandProducts(dto);
        MallFetchPhysicalProductPageVO vo = new MallFetchPhysicalProductPageVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/v1/web_mall/physical_product/{product_id}/info")
    public MallFetchPhysicalProductInfoVO fetchPhysicalProductInfo(@PathVariable("product_id") String productId){
        MallFetchPhysicalProductInfoRO product = physicalProductService.findProductProfile(productId);
        MallFetchPhysicalProductInfoVO vo = new MallFetchPhysicalProductInfoVO();
        vo.setProfile(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

}
