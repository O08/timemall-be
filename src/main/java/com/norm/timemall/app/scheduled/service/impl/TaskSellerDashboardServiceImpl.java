package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskSellerDashboardMapper;
import com.norm.timemall.app.scheduled.service.TaskSellerDashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskSellerDashboardServiceImpl implements TaskSellerDashboardService {
    @Autowired
    private TaskSellerDashboardMapper taskSellerDashboardMapper;
    @Override
    public void doRefreshDashboardInfo() {
        taskSellerDashboardMapper.refreshSellerDashboard();
    }

    @Override
    public void doRefreshDashboardOnlineTimeInfo() {
        taskSellerDashboardMapper.refreshSellerDashboardOnlineTimeInfo();
    }
}
