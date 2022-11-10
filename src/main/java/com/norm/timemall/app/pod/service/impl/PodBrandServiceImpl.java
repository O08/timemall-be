package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.pod.domain.pojo.PodBrandBank;
import com.norm.timemall.app.pod.domain.pojo.PodBrandContact;
import com.norm.timemall.app.pod.domain.pojo.PodPayway;
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
    public PodPayway findPaywayById(String brandId) {
        LambdaQueryWrapper<Brand> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(Brand::getId,brandId);
        Brand brand = podBrandMapper.selectById(brandId);
        PodBrandBank bank = new PodBrandBank();
        bank.setCardholder(brand.getCardholder());
        bank.setCardNo(brand.getCardno());
        PodPayway payway =new PodPayway();
        payway.setBank(bank);
        payway.setAliPay(brand.getAlipay());
        payway.setWechatPay(brand.getWechatpay());
        return payway;
    }
}
