package com.norm.timemall.app.mall.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface MallAffiliateOrderService {
    public void newAffiliateOrder(String cellId, String orderId, String orderType, BigDecimal price, String influencer, String chn, String market);
}
