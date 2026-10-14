package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.pojo.SimpleBrandBank;
import com.norm.timemall.app.pod.domain.pojo.PodBrandContact;
import com.norm.timemall.app.base.pojo.BrandPayway;
import com.norm.timemall.app.pod.mapper.PodBrandMapper;
import com.norm.timemall.app.pod.service.PodBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PodBrandServiceImpl implements PodBrandService {

    @Autowired
    private PodBrandMapper podBrandMapper;
    @Override
    public PodBrandContact findContactById(String brandId) {
        LambdaQueryWrapper<Brand> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Brand::getId,brandId);
        Brand brand = podBrandMapper.selectById(brandId);
        return  new PodBrandContact()
                .setEmail(brand.getEmail())
                .setPhone(brand.getPhone())
                .setWechat(brand.getWechat());
        // todo empty handle
    }

    @Override
    public BrandPayway findPaywayById(String brandId) {
        LambdaQueryWrapper<Brand> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Brand::getId,brandId);
        Brand brand = podBrandMapper.selectById(brandId);
        SimpleBrandBank bank = new SimpleBrandBank();
        bank.setCardholder(brand.getCardholder());
        bank.setCardNo(brand.getCardno());
        BrandPayway payway =new BrandPayway();
        payway.setBank(bank);
        payway.setAliPay(brand.getAlipay());
        payway.setWechatPay(brand.getWechatpay());
        return payway;
    }
}
