package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskOasisBalanceSummaryMapper;
import com.norm.timemall.app.scheduled.service.TaskOasisBalanceSummaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskOasisBalanceSummaryServiceImpl implements TaskOasisBalanceSummaryService {
    @Autowired
    private TaskOasisBalanceSummaryMapper taskOasisBalanceSummaryMapper;

    @Override
    public void doRefreshOasisBalanceSummaryInfo() {
        taskOasisBalanceSummaryMapper.doExecuteProcedure();
    }
}
