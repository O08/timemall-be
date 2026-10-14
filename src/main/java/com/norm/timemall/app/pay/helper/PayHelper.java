package com.norm.timemall.app.pay.helper;

import com.norm.timemall.app.base.enums.PayType;
import com.norm.timemall.app.base.mo.ProprietaryTradingOrder;
import com.norm.timemall.app.base.mo.ProprietaryTradingPayment;
import com.norm.timemall.app.studio.service.StudioProprietaryTradingOrderService;
import com.norm.timemall.app.studio.service.StudioTopUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
@Component
public class PayHelper {


    @Autowired
    private StudioProprietaryTradingOrderService studioProprietaryTradingOrderService;
    @Autowired
    private StudioTopUpService studioTopUpService;
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
    public ProprietaryTradingPayment generatePaymentWhenSuccessForInnerPay(String outTradeNo,String tradeNo,
                                                                         String tradeStatus,String totalAmount,String message){
        ProprietaryTradingPayment tradingPayment = new ProprietaryTradingPayment();
        tradingPayment.setTradingOrderId(outTradeNo)
                .setTradeNo(tradeNo)
                .setStatus(tradeStatus)
                .setPayType(PayType.BALANCE.getCode()+"")
                .setPayTypeDesc(PayType.BALANCE.getDesc())
                .setTotalAmount(totalAmount)
                .setMessage(message);

        return tradingPayment;
    }

    public void busiHandler(Map<String,String> params){
        String out_trade_no = params.get("out_trade_no");
        ProprietaryTradingOrder tradingOrder = studioProprietaryTradingOrderService.getById(out_trade_no);
        String busiType = getBusiType(tradingOrder.getTradingId());
        if("topUp".equals(busiType)){
            studioTopUpService.topUpPostHandler(tradingOrder.getBrandId(),tradingOrder.getTotal());
        }
    }
    public String getPaySubject(String tradingId){
        String subject="";
        switch (tradingId){
            case "prd-0002":
                subject="充值";
                break;
            default:
                subject="自营";
                break;
        }
        return  subject;
    }
    private String getBusiType(String tradingId){
        String busiType="";
        switch (tradingId){
            case "prd-0001":
                busiType="bluesign";
                break;
            case "prd-0002":
                busiType="topUp";
                break;
            default:
                busiType="others";
                break;
        }
        return busiType;
    }

}
