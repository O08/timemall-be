package com.norm.timemall.app.pod.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.AffiliateOrderTypeEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.mo.AffiliateOrder;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.pod.mapper.PodAffiliateOrderMapper;
import com.norm.timemall.app.pod.service.PodAffiliatePayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PodAffiliatePayServiceImpl implements PodAffiliatePayService {
    @Autowired
    private DefaultPayService defaultPayService;

    @Autowired
    private PodAffiliateOrderMapper podAffiliateOrderMapper;

    @Async
    @Override
    public void cellBillRevshare(String influencer, BigDecimal commission,String billId,String supplierId) {


        // pay
        TransferBO bo = generateMillstoneAffiliateTransferBO(commission, influencer, supplierId,billId);
        defaultPayService.transfer(new Gson().toJson(bo));

    }
    @Async
    @Override
    public void planOrderReshare(String influencer, BigDecimal commission,String planOrderId,String supplierId) {
        // pay
        TransferBO bo = generatePlanOrderAffiliateTransferBO(commission, influencer, supplierId,planOrderId);
        defaultPayService.transfer(new Gson().toJson(bo));
    }

    private TransferBO generateMillstoneAffiliateTransferBO(BigDecimal amount, String payeeAccount, String payerAccount, String outNo){
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(payeeAccount)
                .setPayerAccount(payerAccount)
                .setPayerType(FidTypeEnum.BRAND.getMark())
                .setTransType(TransTypeEnum.MILLSTONE_AFFILIATE_PAY.getMark());
        return  bo;
    }
    private TransferBO generatePlanOrderAffiliateTransferBO(BigDecimal amount, String payeeAccount, String payerAccount, String outNo){
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(payeeAccount)
                .setPayerAccount(OperatorConfig.sysCellPlanOrderMidFinAccount)
                .setPayerType(FidTypeEnum.OPERATOR.getMark())
                .setTransType(TransTypeEnum.PLAN_ORDER_AFFILIATE_PAY.getMark());
        return  bo;
    }
}
