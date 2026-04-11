package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.mall.domain.vo.BrandProfileVO;
import org.springframework.stereotype.Service;

@Service
public interface BrandService {
    BrandProfileVO findBrandProfile(String brandId);

    Brand findBrand(String brandId);
}
