package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.TaskAffiliateScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AffiliateETL {
    @Autowired
    private TaskAffiliateScheduleService taskAffiliateScheduleService;
    /**
     * @Scheduled
     * cron 表达式，用于设置每次执行的时间间隔
     */
    @Scheduled( cron = "0 0 0 * * ?",zone = "Asia/Shanghai")	//
    public void scheduleProductInfo() {
        //业务逻辑 scheduled
        taskAffiliateScheduleService.doLoadProductInfo();
    }
    @Scheduled( cron = "0 0 2 * * ?",zone = "Asia/Shanghai")	//
    public void scheduleRefreshInfluencerProduct(){
        taskAffiliateScheduleService.doRefreshInfluencerProduct();
    }
    @Scheduled( cron = "0 0 2 * * ?",zone = "Asia/Shanghai")	//
    public void scheduleRefreshOutreachChannel(){
        taskAffiliateScheduleService.doRefreshOutreachChannel();
    }
    @Scheduled( cron = "0 0 2 * * ?",zone = "Asia/Shanghai")	//
    public void scheduleRefreshLinkMarketing(){
        taskAffiliateScheduleService.doRefreshLinkMarketing();
    }
    @Scheduled( cron = "0 0 2 * * ?",zone = "Asia/Shanghai")	//
    public void scheduleRefreshApiMarketing(){
        taskAffiliateScheduleService.doRefreshApiMarketing();
    }
    @Scheduled( cron = "0 0 3 * * ?",zone = "Asia/Shanghai")	//
    public void scheduleRefreshHotOutreach(){
        taskAffiliateScheduleService.doRefreshHotOutreach();
    }
    @Scheduled( cron = "0 0 3 * * ?",zone = "Asia/Shanghai")	//
    public void scheduleRefreshHotProduct(){
        taskAffiliateScheduleService.doRefreshHotProduct();
    }
    @Scheduled( cron = "0 0 3 * * ?",zone = "Asia/Shanghai")	//
    public void scheduleRefreshDashboard(){
        taskAffiliateScheduleService.doRefreshDashboard();
    }

}
