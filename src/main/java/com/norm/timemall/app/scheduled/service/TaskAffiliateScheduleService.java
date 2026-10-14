package com.norm.timemall.app.scheduled.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

@Service
public interface TaskAffiliateScheduleService {
    void doLoadProductInfo();
    void doRefreshInfluencerProduct();
    void doRefreshOutreachChannel();
    void doRefreshLinkMarketing();
    void doRefreshApiMarketing();

    void doRefreshHotOutreach();
    void doRefreshHotProduct();
    void doRefreshDashboard();


}
