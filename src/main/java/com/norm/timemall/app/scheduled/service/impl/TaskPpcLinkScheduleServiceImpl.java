package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskPpcLinkMapper;
import com.norm.timemall.app.scheduled.service.TaskPpcLinkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskPpcLinkScheduleServiceImpl implements TaskPpcLinkScheduleService {
    @Autowired
    private TaskPpcLinkMapper taskPpcLinkMapper;

    @Override
    public void refreshPpcLinkInfo() {

        taskPpcLinkMapper.updateIpsViews();

    }
}
