package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.studio.domain.pojo.StudioBlueSign;
import com.norm.timemall.app.studio.domain.ro.StudioNewOrderRO;
import org.springframework.stereotype.Service;

@Service
public interface StudioBlueSignService {
    StudioBlueSign findStudioBlueSign(String brandId);
    //蓝标下单
    StudioNewOrderRO newBlueSignOrder();
    boolean payVerify(String id,String stage);

    /**
     * 激活蓝标
     */
    void enableBlueSign();
}
