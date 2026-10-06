package com.norm.timemall.app.mall.service.impl;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.mall.domain.ro.BrandProfileRO;
import com.norm.timemall.app.mall.domain.vo.BrandProfileVO;
import com.norm.timemall.app.mall.mapper.BrandMapper;
import com.norm.timemall.app.mall.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;
    @Override
    public BrandProfileVO findBrandProfile(String brandId) {
        BrandProfileRO profile = brandMapper.selectProfileByBrandId(brandId);
        Authentication authentication = SecurityUserHelper.getCurrentUserAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken && profile!=null){
            profile.setResumeUrl("");
        }
        BrandProfileVO result = new BrandProfileVO();
        result.setProfile(profile)
                .setResponseCode(CodeEnum.SUCCESS)
        ;
        return result;
    }

    @Override
    public Brand findBrand(String brandId) {
        return brandMapper.selectById(brandId);
    }

    @Override
    public BrandProfileVO findBrandBio(String handle) {
        BrandProfileRO profile = brandMapper.selectProfileByBrandHandle(handle);
        Authentication authentication = SecurityUserHelper.getCurrentUserAuthentication();
        if(authentication instanceof AnonymousAuthenticationToken && profile!=null){
            profile.setResumeUrl("");
        }
        BrandProfileVO result = new BrandProfileVO();
        result.setProfile(profile)
                .setResponseCode(CodeEnum.SUCCESS)
        ;
        return result;
    }
}
