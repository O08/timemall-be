package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.TaskOasisBalanceSummaryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OasisBalanceSummaryETL {
    @Autowired
    private TaskOasisBalanceSummaryService taskOasisBalanceSummaryService;
    @Scheduled( cron = "0 0 1 * * ?",zone = "Asia/Shanghai")
    public void refreshOasisBalanceSummaryInfo(){
        log.info("refreshOasisBalanceSummaryInfo etl task start.....");
        taskOasisBalanceSummaryService.doRefreshOasisBalanceSummaryInfo();
        log.info("refreshOasisBalanceSummaryInfo etl task end.....");
    }
}
