package com.norm.timemall.app.ms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.ms.mapper.MsOasisMapper;
import com.norm.timemall.app.ms.service.MsGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MsGroupServiceImpl implements MsGroupService {
    @Autowired
    private MsOasisMapper msOasisMapper;
    @Override
    public boolean beAdmin(String channel) {
        LambdaQueryWrapper<Oasis> queryWrapper= Wrappers.lambdaQuery();
        queryWrapper.eq(Oasis::getId,channel)
                .eq(Oasis::getInitiatorId, SecurityUserHelper.getCurrentPrincipal().getBrandId());
        return msOasisMapper.exists(queryWrapper);
    }
}
