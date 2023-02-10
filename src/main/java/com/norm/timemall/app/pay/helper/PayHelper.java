package com.norm.timemall.app.pay.helper;

import com.norm.timemall.app.base.enums.PayType;
import com.norm.timemall.app.base.mo.ProprietaryTradingPayment;
import com.norm.timemall.app.studio.service.StudioBlueSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class PayHelper {

    @Autowired
    private StudioBlueSignService studioBlueSignService;
    public ProprietaryTradingPayment generatePaymentWhenSuccessForAliPay(String outTradeNo,String tradeNo,
                                                     String tradeStatus,String totalAmount,String message){
        ProprietaryTradingPayment tradingPayment = new ProprietaryTradingPayment();
        tradingPayment.setTradingOrderId(outTradeNo)
                .setTradeNo(tradeNo)
                .setStatus(tradeStatus)
                .setPayType(PayType.ALIPAY.getCode()+"")
                .setPayTypeDesc(PayType.ALIPAY.getDesc())
                .setTotalAmount(totalAmount)
                .setMessage(message);

        return tradingPayment;
    }

    public void busiHandler(String busiType, Map<String,String> params){
        if("bluesign".equals(busiType)){
            studioBlueSignService.enableBlueSign();
        }
    }

}
