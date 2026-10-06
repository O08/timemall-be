package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.norm.timemall.app.base.mo.ProprietaryTradingOrder;
import org.springframework.stereotype.Service;

@Service
public interface StudioProprietaryTradingOrderService  extends IService<ProprietaryTradingOrder> {
    /**
     * 更新订单状态为已支付
     * @param tradingOrderId
     */
    void updateTradingOrderStatusAsPaid(String tradingOrderId);

    void updateTradingOrderStatusAsFail(String tradingOrderId);
}
