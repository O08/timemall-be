package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.ElectricityBusinessTypeEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.OrderStatusEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.ProprietaryTradingOrder;
import com.norm.timemall.app.base.mo.ProprietaryTradingPayment;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.BaseElectricityService;
import com.norm.timemall.app.pay.helper.PayHelper;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.studio.domain.ro.StudioGetElectricityProductInfoRO;
import com.norm.timemall.app.studio.mapper.StudioProprietaryTradingMapper;
import com.norm.timemall.app.studio.mapper.StudioProprietaryTradingOrderMapper;
import com.norm.timemall.app.studio.service.StudioElectricityService;
import com.norm.timemall.app.studio.service.StudioProprietaryTradingOrderService;
import com.norm.timemall.app.studio.service.StudioProprietaryTradingPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioElectricityServiceImpl implements StudioElectricityService {
    private static final int DEFAULT_POINTS=200;

    @Autowired
    private StudioProprietaryTradingMapper tradingMapper;

    @Autowired
    private StudioProprietaryTradingOrderMapper studioProprietaryTradingOrderMapper;

    @Autowired
    private BaseElectricityService baseElectricityService;

    @Autowired
    private DefaultPayService defaultPayService;

    @Autowired
    private PayHelper payHelper;

    @Autowired
    private StudioProprietaryTradingPaymentService studioProprietaryTradingPaymentService;

    @Autowired
    private StudioProprietaryTradingOrderService studioProprietaryTradingOrderService;


    @Override
    public StudioGetElectricityProductInfoRO findProductInfo() {
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return tradingMapper.selectElectricity(currentBrandId);
    }

    @Override
    public void buyElectricity() {
        // validate : max can buy 12 every month
        boolean validated = verifyFrequency();
        if(!validated){
            throw new QuickMessageException("本月次数已用完");
        }
        // find product info
        CustomizeUser buyer = SecurityUserHelper.getCurrentPrincipal();
        StudioGetElectricityProductInfoRO product = tradingMapper.selectElectricity(buyer.getBrandId());

        // new order
        ProprietaryTradingOrder proprietaryTradingOrder=new ProprietaryTradingOrder();
        proprietaryTradingOrder.setId(IdUtil.simpleUUID())
                .setBrandId(buyer.getBrandId())
                .setCustomerId(buyer.getUserId())
                .setTradingId(product.getId())
                .setQuantity(1)
                .setTotal(product.getPrice())
                .setStatus(""+ OrderStatusEnum.CREATING.ordinal())
                .setStatusDesc(OrderStatusEnum.CREATING.name())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioProprietaryTradingOrderMapper.insert(proprietaryTradingOrder);


        // pay process
        TransferBO transferBO = defaultPayService.generateTransferBO(TransTypeEnum.BUY_ELECTRICITY_PAY.getMark(), FidTypeEnum.OPERATOR.getMark(), OperatorConfig.sysMidFinAccount,
                FidTypeEnum.BRAND.getMark(), buyer.getBrandId(), product.getPrice(), proprietaryTradingOrder.getId());

        try {
            String tradeNo = defaultPayService.transfer(new Gson().toJson(transferBO));
            ProprietaryTradingPayment proprietaryTradingPayment = payHelper.generatePaymentWhenSuccessForInnerPay(proprietaryTradingOrder.getId(), tradeNo, "TRADE_SUCCESS", proprietaryTradingOrder.getTotal().toString(), new Gson().toJson(transferBO));
            studioProprietaryTradingPaymentService.insertTradingPayment(proprietaryTradingPayment);
            studioProprietaryTradingOrderService.updateTradingOrderStatusAsPaid(proprietaryTradingOrder.getId());


            // top up electricity
            baseElectricityService.topup(buyer.getBrandId(),DEFAULT_POINTS,"充值电力", ElectricityBusinessTypeEnum.TOP_UP.getMark(), proprietaryTradingOrder.getId(), "补给商城充值电力");

        }catch (ErrorCodeException e){
            studioProprietaryTradingOrderService.updateTradingOrderStatusAsFail(proprietaryTradingOrder.getId());
            throw new ErrorCodeException(e.getCode());
        }
        catch (Exception e){
            studioProprietaryTradingOrderService.updateTradingOrderStatusAsFail(proprietaryTradingOrder.getId());
            throw new QuickMessageException("系统异常");
        }

    }

    private boolean verifyFrequency(){
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
         return  baseElectricityService.findShoppingFrequencyEveryMonth(currentBrandId)<12;
    }
}
