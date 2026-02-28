package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.ProprietaryTradingOrder;
import com.norm.timemall.app.base.mo.ProprietaryTradingPayment;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.base.service.BaseElectricityService;
import com.norm.timemall.app.pay.helper.PayHelper;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.studio.domain.pojo.StudioBlueSign;
import com.norm.timemall.app.studio.mapper.StudioBrandMapper;
import com.norm.timemall.app.studio.mapper.StudioProprietaryTradingMapper;
import com.norm.timemall.app.studio.mapper.StudioProprietaryTradingOrderMapper;
import com.norm.timemall.app.studio.service.StudioBlueSignService;
import com.norm.timemall.app.studio.service.StudioProprietaryTradingOrderService;
import com.norm.timemall.app.studio.service.StudioProprietaryTradingPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioBlueSignServiceImpl implements StudioBlueSignService {

    private static final int DEFAULT_POINTS=200;
    @Autowired
    private StudioProprietaryTradingMapper tradingMapper;


    @Autowired
    private AccountService accountService;

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

    @Autowired
    private StudioBrandMapper studioBrandMapper;


    @Override
    public StudioBlueSign findStudioBlueSign(String brandId) {
        return tradingMapper.selectBlueSign(brandId);
    }

    /**
     * 蓝标下单
     * @return
     */
    @Override
    public void newBlueSignOrder() {
        CustomizeUser buyer = SecurityUserHelper.getCurrentPrincipal();
        // select bluesign info
        Brand brand = accountService.findBrandInfoByUserId(buyer.getUserId());
        StudioBlueSign blueSign = tradingMapper.selectBlueSign(brand.getId());

        // if user already buy and bluesign still valid,return
        if(ObjectUtil.isNotEmpty(blueSign.getBlueEndAt())
                && DateUtil.compare(blueSign.getBlueEndAt(),new Date())>=0){
            throw new ErrorCodeException(CodeEnum.PRODUCT_VALID);
        }

        // new ProprietaryTradingOrder
        ProprietaryTradingOrder proprietaryTradingOrder=new ProprietaryTradingOrder();
        proprietaryTradingOrder.setId(IdUtil.simpleUUID())
                .setBrandId(brand.getId())
                .setCustomerId(buyer.getUserId())
                .setTradingId(blueSign.getId())
                .setQuantity(1)
                .setTotal(NumberUtil.toBigDecimal(blueSign.getPrice()))
                .setStatus(""+OrderStatusEnum.CREATING.ordinal())
                .setStatusDesc(OrderStatusEnum.CREATING.name())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioProprietaryTradingOrderMapper.insert(proprietaryTradingOrder);

        // pay process
        TransferBO transferBO = defaultPayService.generateTransferBO(TransTypeEnum.BUY_BLUE_VIP_PAY.getMark(), FidTypeEnum.OPERATOR.getMark(), OperatorConfig.sysMidFinAccount,
                FidTypeEnum.BRAND.getMark(), buyer.getBrandId(), blueSign.getPrice(), proprietaryTradingOrder.getId());

        try {
            String tradeNo = defaultPayService.transfer(new Gson().toJson(transferBO));

            ProprietaryTradingPayment proprietaryTradingPayment = payHelper.generatePaymentWhenSuccessForInnerPay(proprietaryTradingOrder.getId(), tradeNo, "TRADE_SUCCESS", proprietaryTradingOrder.getTotal().toString(), new Gson().toJson(transferBO));
            studioProprietaryTradingPaymentService.insertTradingPayment(proprietaryTradingPayment);
            studioProprietaryTradingOrderService.updateTradingOrderStatusAsPaid(proprietaryTradingOrder.getId());


            enableBlueSign(proprietaryTradingOrder.getCustomerId());

            // top up electricity 200 points
            baseElectricityService.topup(buyer.getBrandId(),DEFAULT_POINTS,"蓝标权益", ElectricityBusinessTypeEnum.TOP_UP.getMark(), proprietaryTradingOrder.getId(), "开通会员赠送电力");

        }catch (ErrorCodeException e){
            studioProprietaryTradingOrderService.updateTradingOrderStatusAsFail(proprietaryTradingOrder.getId());
            throw new ErrorCodeException(e.getCode());
        }
        catch (Exception e){
            studioProprietaryTradingOrderService.updateTradingOrderStatusAsFail(proprietaryTradingOrder.getId());
            throw new QuickMessageException("系统异常");
        }
    }


    @Override
    public void enableBlueSign(String userId) {
        studioBrandMapper.updateBlueSignByUserId(userId);
    }
}
