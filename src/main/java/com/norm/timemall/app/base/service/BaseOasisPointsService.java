package com.norm.timemall.app.base.service;

import com.norm.timemall.app.base.mo.FinDistribute;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface BaseOasisPointsService {

    FinDistribute findOasisPointsInfo(String targetOasisId, String targetBrandId);
    void topUp(String targetOasisId,String buyerBrandId, BigDecimal points,String item,String businessType,String outNo,String clue);
    void deduct(String targetOasisId,String buyerBrandId, BigDecimal points, String item, String businessType, String outNo, String clue);
}
