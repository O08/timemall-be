package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.OasisMembershipDataProcessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OasisMembershipDataProcessTask {
    @Autowired
    private OasisMembershipDataProcessService oasisMembershipDataProcessService;

    @Scheduled( cron = "0 0 3 * * ?",zone = "Asia/Shanghai")
    public void calOasisMembershipTierStatsInfo(){
        log.info("calOasisMembershipTierStatsInfo data process task start.....");
        oasisMembershipDataProcessService.refreshMembershipTierStatsInfo();
        log.info("calOasisMembershipTierStatsInfo data process task end.....");
    }
}
