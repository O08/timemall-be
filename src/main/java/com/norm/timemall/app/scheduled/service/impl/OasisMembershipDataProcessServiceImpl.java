package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskOasisMembershipTierMapper;
import com.norm.timemall.app.scheduled.service.OasisMembershipDataProcessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OasisMembershipDataProcessServiceImpl implements OasisMembershipDataProcessService {

    @Autowired
    private TaskOasisMembershipTierMapper taskOasisMembershipTierMapper;
    @Override
    public void refreshMembershipTierStatsInfo() {
        taskOasisMembershipTierMapper.callRefreshMembershipTierStatsInfoProcedure();
    }
}
