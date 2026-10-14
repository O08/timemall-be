package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskBrandMpsMetricMapper;
import com.norm.timemall.app.scheduled.service.TaskBrandMpsMetricScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskBrandMpsMetricScheduleServiceImpl implements TaskBrandMpsMetricScheduleService {
    @Autowired
    private TaskBrandMpsMetricMapper taskBrandMpsMetricMapper;
    @Override
    public void refreshMetric() {
        taskBrandMpsMetricMapper.doExecuteProcedure();
    }
}
