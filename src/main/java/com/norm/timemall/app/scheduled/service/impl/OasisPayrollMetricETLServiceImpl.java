package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskOfficePayrollMapper;
import com.norm.timemall.app.scheduled.service.OasisPayrollMetricETLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OasisPayrollMetricETLServiceImpl implements OasisPayrollMetricETLService {

    @Autowired
    private TaskOfficePayrollMapper taskOfficePayrollMapper;
    @Override
    public void calOasisPayrollStatsInfo() {
        taskOfficePayrollMapper.calOasisPayrollStatsInfoProcedure();
    }
}
