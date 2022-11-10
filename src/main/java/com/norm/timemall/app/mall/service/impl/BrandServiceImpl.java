package com.norm.timemall.app.mall.service.impl;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.mall.domain.ro.BrandProfileRO;
import com.norm.timemall.app.mall.domain.vo.BrandProfileVO;
import com.norm.timemall.app.mall.mapper.BrandMapper;
import com.norm.timemall.app.mall.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;
    @Override
    public BrandProfileVO findBrandProfile(String brandId) {
        BrandProfileRO profile = brandMapper.selectProileByBrandId(brandId);
        BrandProfileVO result = new BrandProfileVO();
        result.setProfile(profile)
                .setResponseCode(CodeEnum.SUCCESS)
        ;
        return result;
    }
}
