package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.TaskSimpleProcedureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OasisBalanceSummaryETL {
    @Autowired
    private TaskSimpleProcedureService taskSimpleProcedureService;
    @Scheduled( cron = "0 0 1 * * ?",zone = "Asia/Shanghai")
    public void refreshOasisBalanceSummaryInfo(){
        log.info("refreshOasisBalanceSummaryInfo etl task start.....");
        taskSimpleProcedureService.doRefreshOasisBalanceSummaryInfo();
        log.info("refreshOasisBalanceSummaryInfo etl task end.....");
    }
}
