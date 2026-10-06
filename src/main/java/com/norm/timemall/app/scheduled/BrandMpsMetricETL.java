package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.TaskBrandMpsMetricScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BrandMpsMetricETL {
    @Autowired
    private TaskBrandMpsMetricScheduleService taskBrandMpsMetricScheduleService;
    @Scheduled(cron = "0 0/30 * * * ?",zone = "Asia/Shanghai")
    public  void scheduleCalMpsMetricData(){
        log.info("scheduleCalMpsMetricData etl task start.....");
        taskBrandMpsMetricScheduleService.refreshMetric();
        log.info("scheduleCalMpsMetricData etl  task end.....");
    }
}
