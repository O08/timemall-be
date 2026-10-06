package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.OasisPayrollMetricETLService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OasisPayrollMetricETL {
    @Autowired
    private OasisPayrollMetricETLService oasisPayrollMetricETLService;

    @Scheduled( cron = "0 0 2 * * ?",zone = "Asia/Shanghai")
    public void calOasisPayrollStats(){
        log.info("OasisPayrollMetricETL data process task start.....");
        oasisPayrollMetricETLService.calOasisPayrollStatsInfo();
        log.info("OasisPayrollMetricETL data process task end.....");
    }
}
