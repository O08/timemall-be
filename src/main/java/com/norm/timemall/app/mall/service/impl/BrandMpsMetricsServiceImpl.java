package com.norm.timemall.app.mall.service.impl;

import com.norm.timemall.app.mall.domain.ro.FetchBrandMpsMetricsRO;
import com.norm.timemall.app.mall.mapper.BrandMpsMetricMapper;
import com.norm.timemall.app.mall.service.BrandMpsMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandMpsMetricsServiceImpl implements BrandMpsMetricsService {

    @Autowired
    private BrandMpsMetricMapper brandMpsMetricMapper;

    @Override
    public FetchBrandMpsMetricsRO findMetrics(String brandId) {
        return brandMpsMetricMapper.selectMetricByBrandId(brandId);
    }
}
