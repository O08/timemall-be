package com.norm.timemall.app.pay.helper;

import com.norm.timemall.app.base.enums.PayType;
import com.norm.timemall.app.base.mo.ProprietaryTradingOrder;
import com.norm.timemall.app.base.mo.ProprietaryTradingPayment;
import com.norm.timemall.app.studio.service.StudioBlueSignService;
import com.norm.timemall.app.studio.service.StudioProprietaryTradingOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class PayHelper {

    @Autowired
    private StudioBlueSignService studioBlueSignService;

    @Autowired
    private StudioProprietaryTradingOrderService studioProprietaryTradingOrderService;
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
            String out_trade_no = params.get("out_trade_no");
            ProprietaryTradingOrder tradingOrder = studioProprietaryTradingOrderService.getById(out_trade_no);
            studioBlueSignService.enableBlueSign(tradingOrder.getCustomerId());
        }
    }

}
