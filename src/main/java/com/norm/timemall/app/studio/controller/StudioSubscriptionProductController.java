package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.mo.SubsProduct;
import com.norm.timemall.app.studio.domain.dto.StudioGetSubsProductPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioSubsProductChangeDTO;
import com.norm.timemall.app.studio.domain.dto.StudioSubsProductCreateDTO;
import com.norm.timemall.app.studio.domain.ro.StudioGetSubsProductPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioGetSubsProductPageVO;
import com.norm.timemall.app.studio.service.StudioSubscriptionProductService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioSubscriptionProductController {
    @GetMapping("/api/v1/web_estudio/brand/subscription/product/query")
    public StudioGetSubsProductPageVO getSubsProducts(@Validated StudioGetSubsProductPageDTO dto){

        IPage<StudioGetSubsProductPageRO> product=studioSubscriptionProductService.findProducts(dto);
        StudioGetSubsProductPageVO vo = new StudioGetSubsProductPageVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @Autowired
    private StudioSubscriptionProductService studioSubscriptionProductService;

    @PostMapping("/api/v1/web_estudio/brand/subscription/product/new")
    public SuccessVO createNewProduct(@Validated @RequestBody StudioSubsProductCreateDTO dto){

        studioSubscriptionProductService.newProduct(dto);
        return  new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_estudio/brand/subscription/product/change")
    public SuccessVO changeProduct(@Validated @RequestBody StudioSubsProductChangeDTO dto){

        studioSubscriptionProductService.modifyProduct(dto);
        return  new SuccessVO(CodeEnum.SUCCESS);

    }
    @DeleteMapping("/api/v1/web_estudio/brand/subscription/product/{id}/del")
    public SuccessVO delProduct(@PathVariable("id") String id){

        studioSubscriptionProductService.delProduct(id);
        return  new SuccessVO(CodeEnum.SUCCESS);

    }

}
