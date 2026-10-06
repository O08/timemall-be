package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskSimpleProcedureMapper;
import com.norm.timemall.app.scheduled.service.TaskSimpleProcedureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskSimpleProcedureServiceImpl implements TaskSimpleProcedureService {
    @Autowired
    private TaskSimpleProcedureMapper taskSimpleProcedureMapper;

    @Override
    public void doRefreshOasisBalanceSummaryInfo() {
        taskSimpleProcedureMapper.doExecuteCalOasisBalanceSummaryInfoProcedure();
    }

    @Override
    public void doRefreshMpsDrawerSummaryInfo() {
        taskSimpleProcedureMapper.doExecuteCalMpsDrawerSummaryProcedure();
    }
}
