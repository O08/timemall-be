package com.norm.timemall.app.affiliate.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.affiliate.domain.dto.FetchProductGalleryPageDTO;
import com.norm.timemall.app.affiliate.domain.ro.FetchProductGalleryRO;
import com.norm.timemall.app.affiliate.domain.vo.FetchProductGalleryVO;
import com.norm.timemall.app.affiliate.service.AffiliateProductIndService;
import com.norm.timemall.app.base.enums.CodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    @Autowired
    private AffiliateProductIndService affiliateProductIndService;

    @ResponseBody
    @GetMapping("/api/v1/web/affiliate/product")
    public FetchProductGalleryVO fetchProductGallery(@Validated FetchProductGalleryPageDTO dto){
        IPage<FetchProductGalleryRO> product=affiliateProductIndService.findProductGallery(dto);
        FetchProductGalleryVO vo = new FetchProductGalleryVO();
        vo.setProduct(product);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
}
