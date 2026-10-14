package com.norm.timemall.app.mall.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.ro.FetchBrandMpsMetricsRO;
import com.norm.timemall.app.mall.domain.vo.FetchBrandMpsMetricsVO;
import com.norm.timemall.app.mall.service.BrandMpsMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BrandMpsMetricsController {
    @Autowired
    private BrandMpsMetricsService brandMpsMetricsService;
    @GetMapping("/api/v1/web_mall/brand/{id}/mps/metrics/query")
    private FetchBrandMpsMetricsVO fetchBrandMpsMetrics(@PathVariable("id") String brandId){

        FetchBrandMpsMetricsRO metrics= brandMpsMetricsService.findMetrics(brandId);
        FetchBrandMpsMetricsVO vo =new FetchBrandMpsMetricsVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setMetric(metrics);
        return vo;

    }
}
