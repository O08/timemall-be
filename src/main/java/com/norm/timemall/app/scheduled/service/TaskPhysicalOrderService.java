package com.norm.timemall.app.scheduled.service;

import com.norm.timemall.app.base.mo.PhysicalOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TaskPhysicalOrderService {
    List<PhysicalOrder> getTimeoutUnpaidRecords();

    void cancelTimeoutOrder(String id);
}
