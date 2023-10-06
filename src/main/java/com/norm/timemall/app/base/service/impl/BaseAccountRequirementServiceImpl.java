package com.norm.timemall.app.base.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseAccountRequirementsMapper;
import com.norm.timemall.app.base.mo.DelAccountRequirements;
import com.norm.timemall.app.base.service.BaseAccountRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseAccountRequirementServiceImpl implements BaseAccountRequirementService {
    @Autowired
    private BaseAccountRequirementsMapper baseAccountRequirementsMapper;

    @Override
    public void callGenRequirementFunId(String userId, String brandId) {
        baseAccountRequirementsMapper.callGenRequirementFunId(userId,brandId);
    }

    @Override
    public boolean checkRequirement() {
        LambdaQueryWrapper<DelAccountRequirements> delWrapper= Wrappers.lambdaQuery();
        delWrapper.eq(DelAccountRequirements::getUserId, SecurityUserHelper.getCurrentPrincipal().getUserId());
        List<DelAccountRequirements> requirements = baseAccountRequirementsMapper.selectList(delWrapper);
        long cnt = requirements.stream().filter(e -> !e.getCurrentVal().equals(e.getTargetVal())).count();
        return cnt>0;
    }
}
