package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.ro.CellPlanOrderRO;
import org.springframework.stereotype.Service;

@Service
public interface CellPlanOrderService {
    String newOrder(String planId);

    CellPlanOrderRO findOrder(String orderId);
}
