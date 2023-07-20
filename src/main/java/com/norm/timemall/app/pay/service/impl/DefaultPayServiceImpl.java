package com.norm.timemall.app.pay.service.impl;

import com.google.gson.Gson;
import com.norm.timemall.app.base.mo.PayRefund;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.handler.PayRefundHandler;
import com.norm.timemall.app.pay.handler.PayTransferHandler;
import com.norm.timemall.app.pay.service.DefaultPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class DefaultPayServiceImpl implements DefaultPayService {
    @Autowired
    private PayTransferHandler payTransferHandler;
    @Autowired
    private PayRefundHandler payRefundHandler;
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


}
