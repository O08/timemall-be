package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.TaskRiskAuditScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class RiskAuditETL {
    @Autowired
    private TaskRiskAuditScheduleService taskRiskAuditScheduleService;

    @Scheduled( cron = "0 0 5 * * ?",zone = "Asia/Shanghai")
    public void sendRiskAuditReportEmail(){
        taskRiskAuditScheduleService.doRefreshRiskAudit();
    }
}
