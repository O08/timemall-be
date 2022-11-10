package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.mall.domain.vo.BrandProfileVO;
import com.norm.timemall.app.mall.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandController {

    @Autowired
    private BrandService brandService;
    /*
     * 供应商资料
     */
    @ResponseBody
    @GetMapping(value = "/api/v1/web_mall/brand/{brand_id}/profile")
    public BrandProfileVO retrieveCellIntro(@PathVariable("brand_id") String brandId)
    {
        BrandProfileVO result = brandService.findBrandProfile(brandId);
        return result;
    }
}
