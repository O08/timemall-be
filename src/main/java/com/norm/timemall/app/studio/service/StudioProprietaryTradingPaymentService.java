package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.norm.timemall.app.base.mo.ProprietaryTradingPayment;
import org.springframework.stereotype.Service;

@Service
public interface StudioProprietaryTradingPaymentService extends IService<ProprietaryTradingPayment> {
    void insertTradingPayment(ProprietaryTradingPayment tradingPayment);
}
