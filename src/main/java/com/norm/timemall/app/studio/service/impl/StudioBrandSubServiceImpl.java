package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.BrandSub;
import com.norm.timemall.app.studio.domain.dto.StudioBrandBasicSetting;
import com.norm.timemall.app.studio.mapper.StudioBrandSubMapper;
import com.norm.timemall.app.studio.service.StudioBrandSubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioBrandSubServiceImpl implements StudioBrandSubService {
    @Autowired
    private StudioBrandSubMapper studioBrandSubMapper;

    @Override
    public void modifyBrandSub(StudioBrandBasicSetting dto) {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // validate if occupationCode is '0',need to check selfDefinedOccupation
        String selfDefinedOccupationCode = "0";
        String orgTypeCode="1";
        if(selfDefinedOccupationCode.equals(dto.getOccupationCode()) && dto.getSelfDefinedOccupation().isEmpty()){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // validate if brand type is org,need to check type of business
        if(orgTypeCode.equals(dto.getBrandTypeCode()) && dto.getTypeOfBusiness().isEmpty()){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // delete data
        LambdaQueryWrapper<BrandSub> delWrapper=Wrappers.lambdaQuery();
        delWrapper.eq(BrandSub::getBrandId, brandId);
        studioBrandSubMapper.delete(delWrapper);
        // insert data
        BrandSub brandSub = new BrandSub();
        brandSub.setId(IdUtil.simpleUUID())
                .setBrandId(brandId)
                .setBrandTypeCode(dto.getBrandTypeCode())
                .setIndustryCode(dto.getIndustryCode())
                .setOccupationCode(dto.getOccupationCode())
                .setSelfDefinedOccupation(dto.getSelfDefinedOccupation())
                .setSupportFreeCooperation(dto.getSupportFreeCooperation())
                .setCooperationScope(dto.getCooperationScope())
                .setAvailableForWork(dto.getAvailableForWork())
                .setHiring(dto.getHiring())
                .setTypeOfBusiness(dto.getTypeOfBusiness())
                .setIndustry(dto.getIndustry())
                .setFreeNightCounsellor(dto.getFreeNightCounsellor());
        studioBrandSubMapper.insert(brandSub);
    }
}
