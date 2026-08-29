package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskAppRedeemOrderMapper;
import com.norm.timemall.app.scheduled.mapper.TaskAppRedeemProductStatsMapper;
import com.norm.timemall.app.scheduled.service.TaskAppRedeemScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskAppRedeemScheduleServiceImpl implements TaskAppRedeemScheduleService {

    @Autowired
    private TaskAppRedeemProductStatsMapper taskAppRedeemProductStatsMapper;
    @Autowired
    private TaskAppRedeemOrderMapper taskAppRedeemOrderMapper;
    @Override
    public void doRestMonthBuyerOrders() {
       taskAppRedeemProductStatsMapper.resetMonthBuyerOrders();
    }

    @Override
    public void doRefreshAppRedeemOrderDashboard() {
        taskAppRedeemOrderMapper.callRefreshAppRedeemOrderDashboardProcedure();
    }
}
