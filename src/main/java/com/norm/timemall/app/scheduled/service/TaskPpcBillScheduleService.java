package com.norm.timemall.app.scheduled.service;

import org.springframework.stereotype.Service;

@Service
public interface TaskPpcBillScheduleService {
    void generatePpcBill(String batch);

    void autoPayPpcBill();


}
