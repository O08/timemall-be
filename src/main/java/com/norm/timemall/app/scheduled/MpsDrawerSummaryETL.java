package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.TaskSimpleProcedureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MpsDrawerSummaryETL {
    @Autowired
    private TaskSimpleProcedureService taskSimpleProcedureService;
    @Scheduled( cron = "0 0 1 * * ?",zone = "Asia/Shanghai")
    public void refreshMpsDrawerSummaryInfo(){
        log.info("refreshMpsDrawerSummaryInfo etl task start.....");
        taskSimpleProcedureService.doRefreshMpsDrawerSummaryInfo();
        log.info("refreshMpsDrawerSummaryInfo etl task end.....");
    }
}
