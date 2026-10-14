package com.norm.timemall.app.scheduled.service;

import org.springframework.stereotype.Service;

@Service
public interface TaskRiskAuditScheduleService {
    void sendRiskAuditEmail();
    void doRefreshRiskAudit();
}
