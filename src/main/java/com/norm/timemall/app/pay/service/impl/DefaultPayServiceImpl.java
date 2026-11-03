package com.norm.timemall.app.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.mapper.FinAccountMapper;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.PayRefund;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.handler.PayRefundHandler;
import com.norm.timemall.app.pay.handler.PayTransferHandler;
import com.norm.timemall.app.pay.service.DefaultPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Slf4j
@Service
public class DefaultPayServiceImpl implements DefaultPayService {
    @Autowired
    private PayTransferHandler payTransferHandler;
    @Autowired
    private PayRefundHandler payRefundHandler;
    @Autowired
    private FinAccountMapper finAccountMapper;

    @Override
    public FinAccount findBalanceInfo(String fidType, String fid) {
        LambdaQueryWrapper<FinAccount> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(FinAccount::getFid,fid);
        wrapper.eq(FinAccount::getFidType,fidType);
        return finAccountMapper.selectOne(wrapper);
    }

    @Override
    public String transfer(String param) {
        return payTransferHandler.doTransfer(param);
    }

    @Override
    public void refund(String param) {
        TransferBO refundTransferBO = payRefundHandler.getRefundTransferBO(param);
        PayRefund payRefund = payRefundHandler.newRefundRecord(param);
        // refund
        String tradeNo = transfer(new Gson().toJson(refundTransferBO));
        payRefundHandler.modifyRefundRecordWhenRefundSuccess(payRefund,tradeNo);
    }

    @Override
    public TransferBO generateTransferBO(String transType, String payeeType, String payeeAccount, String payerType, String payerAccount, BigDecimal amount, String outNo) {
        TransferBO transferBO=new TransferBO();
        transferBO.setOutNo(outNo)
                .setAmount(amount)
                .setTransType(transType)
                .setPayeeType(payeeType)
                .setPayeeAccount(payeeAccount)
                .setPayerType(payerType)
                .setPayerAccount(payerAccount);
        return transferBO;
    }


}
