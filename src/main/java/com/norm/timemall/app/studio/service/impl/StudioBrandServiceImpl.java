package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.pojo.SimpleBrandBank;
import com.norm.timemall.app.base.pojo.BrandInfo;
import com.norm.timemall.app.base.pojo.BrandPayway;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.util.mate.MybatisMateEncryptor;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.pojo.StudioBank;
import com.norm.timemall.app.base.pojo.BrandContact;
import com.norm.timemall.app.studio.mapper.StudioBrandMapper;
import com.norm.timemall.app.studio.service.StudioBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudioBrandServiceImpl implements StudioBrandService {
    @Autowired
    private StudioBrandMapper studioBrandMapper;
    @Autowired
    private MybatisMateEncryptor mybatisMateEncryptor;
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
        String cardholder= mybatisMateEncryptor.defaultEncrypt(bank.getCardholder());
        String cardNo= mybatisMateEncryptor.defaultEncrypt(bank.getCardNo());
        studioBrandMapper.updateBrandBankById(brandId,userId,cardholder,cardNo);
    }

    @Override
    public void modifyBrandContact(String brandId, StudioContactDTO contact) {
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();
        String email= CharSequenceUtil.isEmpty(contact.getEmail()) ? "" : mybatisMateEncryptor.defaultEncrypt(contact.getEmail());
        String phone= CharSequenceUtil.isEmpty(contact.getPhone()) ? "" : mybatisMateEncryptor.defaultEncrypt(contact.getPhone());
        studioBrandMapper.updateBrandContactById(brandId, user.getUserId(), email,phone);
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
    public BrandInfo findBrandInfoByUserId(String userId) {
        LambdaQueryWrapper<Brand> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Brand::getCustomerId,userId);
        Brand brand = studioBrandMapper.selectOne(wrapper);
        SimpleBrandBank bank = new SimpleBrandBank();
        bank.setCardholder(brand.getCardholder());
        bank.setCardNo(brand.getCardno());
        BrandPayway payway =new BrandPayway();
        payway.setBank(bank)
                .setAliPay(brand.getAlipay())
                .setWechatPay(brand.getWechatpay());
        BrandContact contact = new BrandContact();
        contact.setEmail(brand.getEmail())
                .setPhone(brand.getPhone())
                .setWechat(brand.getWechat());

        BrandInfo brandInfo = new BrandInfo();
        brandInfo.setContact(contact)
                .setPayway(payway);
        brandInfo.setBrand(brand.getBrandName());
        brandInfo.setAvatar(brand.getAvator());
        return  brandInfo;

    }

    @Override
    public void modifyBrandWechatQr(String brandId, String uri) {
        studioBrandMapper.updateBrandWechatQr(brandId,uri);
    }

    @Override
    public void modifyBrandBasic(String brandId, String userId, StudioBrandBasicInfoDTO dto) {
        LambdaQueryWrapper<Brand> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(Brand::getBrandName,dto.getBrand());
        Brand brand = studioBrandMapper.selectOne(wrapper);
        if(ObjectUtil.isNotNull(brand) && (!brandId.equals(brand.getId())) ){
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_NAME_EXIST);
        }

        studioBrandMapper.updateBrandBasicInfo(brandId,userId,dto);
    }

    @Override
    public void modifyBrandSkills(String brandId, String userId, StudioBrandSkillsDTO dto) {
        Gson gson = new Gson();
        studioBrandMapper.updateBrandSkills(brandId,userId,gson.toJson(dto.getSkill().getSkills()));
    }

    @Override
    public void modifyBrandExperience(String brandId, String userId, StudioBrandExperienceDTO dto) {
        Gson gson = new Gson();
        studioBrandMapper.updateBrandExperience(brandId,userId,gson.toJson(dto.getHistory().getExperience()));
    }

    @Override
    public void modifyBrandLinks(StudioBrandLinksDTO dto) {
        Gson gson = new Gson();
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        studioBrandMapper.updateBrandLinks(brandId,gson.toJson(dto.getLink().getRecords()));

    }
}
