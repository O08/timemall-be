package com.norm.timemall.app.mall.service.impl;

import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.mall.domain.pojo.FetchDelAccountRequirement;
import com.norm.timemall.app.mall.domain.ro.FetchDelAccountRequirementRO;
import com.norm.timemall.app.mall.mapper.DelAccountRequirementsMapper;
import com.norm.timemall.app.mall.service.DelAccountRequirementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DelAccountRequirementServiceImpl implements DelAccountRequirementService {

    @Autowired
    private DelAccountRequirementsMapper delAccountRequirementsMapper;
    @Override
    public FetchDelAccountRequirement findRequirement() {

        String userId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        ArrayList<FetchDelAccountRequirementRO> records=delAccountRequirementsMapper.selectRequirementsByUserId(userId);
        FetchDelAccountRequirement requirement = new FetchDelAccountRequirement();
        requirement.setRecords(records);
        return requirement;

    }
}
