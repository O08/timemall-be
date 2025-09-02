package com.norm.timemall.app.base.service;

import org.springframework.stereotype.Service;

@Service
public interface BaseElectricityService {
    Long findShoppingFrequencyEveryMonth(String buyerBrandId);

    void topup(String buyerBrandId, int defaultPoints,String item,String businessType,String outNo,String clue);

}
