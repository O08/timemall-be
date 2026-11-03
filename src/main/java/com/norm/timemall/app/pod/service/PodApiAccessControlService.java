package com.norm.timemall.app.pod.service;

import org.springframework.stereotype.Service;

@Service
public interface PodApiAccessControlService {
    boolean isCellPlanOrderDeliverReceiver(String orderId,String tag);
}
