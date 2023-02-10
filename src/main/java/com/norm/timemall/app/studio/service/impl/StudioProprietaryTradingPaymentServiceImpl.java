package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.norm.timemall.app.base.mo.ProprietaryTradingPayment;
import com.norm.timemall.app.studio.mapper.StudioProprietaryTradingPaymentMapper;
import com.norm.timemall.app.studio.service.StudioProprietaryTradingPaymentService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioProprietaryTradingPaymentServiceImpl extends ServiceImpl<StudioProprietaryTradingPaymentMapper, ProprietaryTradingPayment> implements StudioProprietaryTradingPaymentService {
    @Override
    public void insertTradingPayment(ProprietaryTradingPayment tradingPayment) {
        ProprietaryTradingPayment proprietaryTradingPayment = new ProprietaryTradingPayment();
        proprietaryTradingPayment.setId(IdUtil.simpleUUID())
                .setTradingOrderId(tradingPayment.getTradingOrderId())
                .setTradeNo(tradingPayment.getTradeNo())
                .setStatus(tradingPayment.getStatus())
                .setStatusDesc(tradingPayment.getStatusDesc())
                .setPayType(tradingPayment.getPayType())
                .setPayTypeDesc(tradingPayment.getPayTypeDesc())
                .setMessage(tradingPayment.getMessage())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        this.save(proprietaryTradingPayment);
    }
}
