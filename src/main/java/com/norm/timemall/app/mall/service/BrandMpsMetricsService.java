package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.ro.FetchBrandMpsMetricsRO;
import org.springframework.stereotype.Service;

@Service
public interface BrandMpsMetricsService {
    FetchBrandMpsMetricsRO findMetrics(String brandId);

}
