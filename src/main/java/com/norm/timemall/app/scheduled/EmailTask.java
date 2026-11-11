package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.scheduled.service.TaskRiskAuditScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class EmailTask {
    @Autowired
    private TaskRiskAuditScheduleService taskRiskAuditScheduleService;

    @Scheduled( cron = "0 0 12 * * ?",zone = "Asia/Shanghai")
    public void sendRiskAuditReportEmail(){
        taskRiskAuditScheduleService.sendRiskAuditEmail();
    }
}
