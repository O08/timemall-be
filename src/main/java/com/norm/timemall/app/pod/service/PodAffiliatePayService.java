package com.norm.timemall.app.pod.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface PodAffiliatePayService {
     void cellBillRevshare(String influencer, BigDecimal commission,String billId,String supplierId);
     void planOrderReshare(String influencer, BigDecimal commission,String planOrderId,String supplierId);
}
