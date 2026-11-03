package com.norm.timemall.app.mall.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.dto.MallRetrieveBrandProductListPageDTO;
import com.norm.timemall.app.mall.domain.dto.MallRetrieveProductListPageDTO;
import com.norm.timemall.app.mall.domain.ro.MallRetrieveBrandProductListPageRO;
import com.norm.timemall.app.mall.domain.ro.MallRetrieveProductListPageRO;
import com.norm.timemall.app.mall.domain.vo.MallFetchVirtualProductProfileVO;
import com.norm.timemall.app.mall.domain.vo.MallRetrieveBrandProductListPageVO;
import com.norm.timemall.app.mall.domain.vo.MallRetrieveProductListPageVO;
import com.norm.timemall.app.mall.service.MallVirtualProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MallVirtualProductController {
    @Autowired
    MallVirtualProductService mallVirtualProductService;

    @GetMapping(value = "/api/v1/web_mall/virtual/product")
    public MallRetrieveProductListPageVO retrieveProductList(@Validated MallRetrieveProductListPageDTO dto){

        IPage<MallRetrieveProductListPageRO> product = mallVirtualProductService.findProducts(dto);
        MallRetrieveProductListPageVO vo = new MallRetrieveProductListPageVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @GetMapping("/api/v1/web_mall/brand/virtual/product")
    public MallRetrieveBrandProductListPageVO retrieveBrandProductList(@Validated MallRetrieveBrandProductListPageDTO dto){

        IPage<MallRetrieveBrandProductListPageRO> product= mallVirtualProductService.findBrandProducts(dto);
        MallRetrieveBrandProductListPageVO vo = new MallRetrieveBrandProductListPageVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @GetMapping("/api/v1/web_mall/virtual/product/{id}/profile")
    public MallFetchVirtualProductProfileVO fetchVirtualProductProfile(@PathVariable("id") String id){

       return mallVirtualProductService.findProductProfile(id);

    }
}
