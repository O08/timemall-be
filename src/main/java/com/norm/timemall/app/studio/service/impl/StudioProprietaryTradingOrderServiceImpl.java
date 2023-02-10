package com.norm.timemall.app.studio.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.norm.timemall.app.base.enums.OrderStatusEnum;
import com.norm.timemall.app.base.mo.ProprietaryTradingOrder;
import com.norm.timemall.app.studio.mapper.StudioProprietaryTradingOrderMapper;
import com.norm.timemall.app.studio.service.StudioProprietaryTradingOrderService;
import org.springframework.stereotype.Service;

@Service
public class StudioProprietaryTradingOrderServiceImpl  extends ServiceImpl<StudioProprietaryTradingOrderMapper, ProprietaryTradingOrder> implements StudioProprietaryTradingOrderService {
    @Override
    public void updateTradingOrderStatusAsPaid(String tradingOrderId) {
        this.baseMapper.updateTradingOrderStatus(tradingOrderId, OrderStatusEnum.PAID.name());
    }
}
