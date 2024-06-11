package com.norm.timemall.app.mall.service;

import com.norm.timemall.app.mall.domain.dto.AffiliateDTO;
import com.norm.timemall.app.mall.domain.ro.CellPlanOrderRO;
import org.springframework.stereotype.Service;

@Service
public interface CellPlanOrderService {
    String newOrder(String planId, AffiliateDTO dto);

    CellPlanOrderRO findOrder(String orderId);
}
