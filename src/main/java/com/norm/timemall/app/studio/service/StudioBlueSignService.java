package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.pojo.StudioBlueSign;
import com.norm.timemall.app.base.pojo.ro.NewOrderRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioBlueSignService {
    StudioBlueSign findStudioBlueSign(String brandId);
    //蓝标下单
    void newBlueSignOrder();

    /**
     * 激活蓝标
     */
    void enableBlueSign(String userId);
}
