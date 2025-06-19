package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.TaskSellerDashboardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SellerDashboardETL {
    @Autowired
    private TaskSellerDashboardService taskSellerDashboardService;


    //@Scheduled( cron = "0/30 * * * * ?",zone = "Asia/Shanghai")
    @Scheduled( cron = "0 0 1 * * ?",zone = "Asia/Shanghai")
    public void refreshDashboardInfo(){
        log.info("refreshDashboardInfo etl task start.....");
        taskSellerDashboardService.doRefreshDashboardInfo();
        log.info("refreshDashboardInfo etl task end.....");
    }
}
