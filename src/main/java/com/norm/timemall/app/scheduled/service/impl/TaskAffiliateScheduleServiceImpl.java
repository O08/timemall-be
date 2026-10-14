package com.norm.timemall.app.scheduled.service.impl;

import com.norm.timemall.app.scheduled.mapper.TaskAffiliateScheduleMapper;
import com.norm.timemall.app.scheduled.service.TaskAffiliateScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Slf4j
@Service
public class TaskAffiliateScheduleServiceImpl implements TaskAffiliateScheduleService {

    @Autowired
    private TaskAffiliateScheduleMapper taskAffiliateScheduleMapper;
    @Override
    public void doLoadProductInfo() {
        taskAffiliateScheduleMapper.loadAffiliateProduct();
    }

    @Override
    public void doRefreshInfluencerProduct() {
        taskAffiliateScheduleMapper.refreshInfluencerProduct();
    }

    @Override
    public void doRefreshOutreachChannel() {
        taskAffiliateScheduleMapper.refreshOutreachChannel();
    }

    @Override
    public void doRefreshLinkMarketing() {
        taskAffiliateScheduleMapper.refreshLinkMarketing();

    }

    @Override
    public void doRefreshApiMarketing() {
        taskAffiliateScheduleMapper.refreshApiMarketing();
    }

    @Override
    public void doRefreshHotOutreach() {
        //yesterday week month
        taskAffiliateScheduleMapper.refreshHotOutreach("yesterday");
        taskAffiliateScheduleMapper.refreshHotOutreach("week");
        taskAffiliateScheduleMapper.refreshHotOutreach("month");

    }

    @Override
    public void doRefreshHotProduct() {
        taskAffiliateScheduleMapper.refreshHotProduct("yesterday");
        taskAffiliateScheduleMapper.refreshHotProduct("week");
        taskAffiliateScheduleMapper.refreshHotProduct("month");


    }

    @Override
    public void doRefreshDashboard() {
        taskAffiliateScheduleMapper.refreshDashboard("yesterday");
        taskAffiliateScheduleMapper.refreshDashboard("week");
        taskAffiliateScheduleMapper.refreshDashboard("month");


    }
}
