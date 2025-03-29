package com.norm.timemall.app.mall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.BluvarrierRoleEnum;
import com.norm.timemall.app.base.mo.Bluvarrier;
import com.norm.timemall.app.mall.domain.ro.FetchBluvarrierRO;
import com.norm.timemall.app.mall.mapper.MallBluvarrierMapper;
import com.norm.timemall.app.mall.service.BluvarrierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BluvarrierServiceImpl implements BluvarrierService {
    @Autowired
    private MallBluvarrierMapper mallBluvarrierMapper;

    @Override
    public FetchBluvarrierRO findBluvarrier() {

        LambdaQueryWrapper<Bluvarrier> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark());
        Bluvarrier bluvarrier = mallBluvarrierMapper.selectOne(lambdaQueryWrapper);
        FetchBluvarrierRO ro=new FetchBluvarrierRO();
        ro.setCustomerId(bluvarrier.getCustomerId());
        return ro;

    }
}
