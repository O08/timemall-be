package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.TransQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransQueryETL {
    @Autowired
    private TransQueryService transQueryService;

    @Scheduled(cron = "0 0/30 * * * ?",zone = "Asia/Shanghai")
    public  void scheduleLoadTransData(){
        log.info("scheduleLoadTransData etl task start.....");
        transQueryService.loadTransData();
        log.info("scheduleLoadTransData etl task end.....");
    }

}
