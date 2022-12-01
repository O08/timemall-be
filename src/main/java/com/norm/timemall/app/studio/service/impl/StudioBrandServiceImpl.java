package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.studio.domain.dto.StudioBrandBankDTO;
import com.norm.timemall.app.studio.domain.dto.StudioBrandProfileDTO;
import com.norm.timemall.app.studio.domain.dto.StudioContactDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioBank;
import com.norm.timemall.app.studio.domain.pojo.StudioBrandContact;
import com.norm.timemall.app.studio.mapper.StudioBrandMapper;
import com.norm.timemall.app.studio.service.StudioBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioBrandServiceImpl implements StudioBrandService {
    @Autowired
    private StudioBrandMapper studioBrandMapper;
    @Override
    public void modifyBrandProfile(String brandId, String userId,StudioBrandProfileDTO dto) {
        Gson gson = new Gson();
        LambdaQueryWrapper<Brand> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Brand::getId,brandId)
                .eq(Brand::getCustomerId,userId);
        Brand brand = studioBrandMapper.selectOne(wrapper);
        // access control
        if(brand ==null){
            throw new ErrorCodeException(CodeEnum.INVALID_TOKEN);
        }
        brand.setBrandName(dto.getProfile().getBrand());
        brand.setAvator(dto.getProfile().getAvator());
        brand.setTitle(dto.getProfile().getTitle());
        brand.setLocation(dto.getProfile().getLocation());
        brand.setExperience(gson.toJson(dto.getProfile().getExperience()));
        brand.setSkills(gson.toJson(dto.getProfile().getSkills()));
        studioBrandMapper.updateById(brand);
    }

    @Override
    public void modifyBrandBank(String brandId, String userId,StudioBrandBankDTO dto) {
        StudioBank bank = dto.getBank();
        studioBrandMapper.updateBrandBankById(brandId,userId,bank.getCardholder(),bank.getCardNo());
    }

    @Override
    public void modifyBrandContact(String brandId, StudioContactDTO contact) {
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();
        studioBrandMapper.updateBrandContactById(brandId, user.getUserId(), contact.getEmail(),contact.getPhone());
    }

    @Override
    public void modifyAliPay(String brandId, String uri) {
        studioBrandMapper.updateAliPayById(brandId,uri);
    }

    @Override
    public Brand findbyId(String brandId) {

        Brand brand = studioBrandMapper.selectById(brandId);

        return brand;
    }

    @Override
    public void modifyWeChatPay(String brandId, String uri) {
        studioBrandMapper.updateWechatPayById(brandId,uri);
    }

    @Override
    public void modifyBrandCover(String brandId, String uri) {
        studioBrandMapper.updateBrandCoverById(brandId,uri);
    }

    @Override
    public void modifyBrandAvatar(String brandId, String uri) {
        studioBrandMapper.updateBrandAvatar(brandId,uri);
    }

    @Override
    public StudioBrandContact findContactByUserId(String userId) {
        LambdaQueryWrapper<Brand> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Brand::getCustomerId,userId);
        Brand brand = studioBrandMapper.selectOne(wrapper);
        StudioBrandContact contact = new StudioBrandContact();
        contact.setEmail(brand.getEmail())
                .setPhone(brand.getPhone())
                .setWechat(brand.getWechat());
        return  contact;

    }
}
