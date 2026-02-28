package com.norm.timemall.app.pay.handler;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.PayRefundStatusEnum;
import com.norm.timemall.app.base.enums.PayTransferStatusEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.mo.PayRefund;
import com.norm.timemall.app.base.mo.PayTransfer;
import com.norm.timemall.app.base.pojo.RefundBO;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.mapper.PayRefundMapper;
import com.norm.timemall.app.pay.mapper.PayTransferMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PayRefundHandler {
    @Autowired
    private PayRefundMapper payRefundMapper;
    @Autowired
    private PayTransferMapper payTransferMapper;
    public TransferBO getRefundTransferBO(String param){
        RefundBO refundBO = new Gson().fromJson(param, RefundBO.class);
        // query refund is success
        LambdaQueryWrapper<PayRefund> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(PayRefund::getTradeNo,refundBO.getTradeNo())
                        .eq(PayRefund::getStatus, PayRefundStatusEnum.REFUND_SUCCESS.name());
        boolean alreadyRefund = payRefundMapper.exists(wrapper);
        if(alreadyRefund){
            throw new ErrorCodeException(CodeEnum.REFUND_REPEAT);
        }
        LambdaQueryWrapper<PayTransfer> transferLambdaQueryWrapper=Wrappers.lambdaQuery();
        transferLambdaQueryWrapper.eq(PayTransfer::getTradeNo,refundBO.getTradeNo())
                        .eq(PayTransfer::getTradingOrderId,refundBO.getOutNo());
        PayTransfer transfer = payTransferMapper.selectOne(transferLambdaQueryWrapper);
        if(transfer==null || !transfer.getStatus().equals(PayTransferStatusEnum.TRANSFER_SUCCESS.name())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        TransferBO transferBO = new Gson().fromJson(transfer.getMessage(), TransferBO.class);
        TransferBO refundTransferBO = new TransferBO();
        refundTransferBO.setTransType(TransTypeEnum.REFUND.getMark())
                .setAmount(transferBO.getAmount())
                .setOutNo(transferBO.getOutNo())
                .setPayeeType(transferBO.getPayerType())
                .setPayeeAccount(transferBO.getPayerAccount())
                .setPayerType(transferBO.getPayeeType())
                .setPayerAccount(transferBO.getPayeeAccount());

        return refundTransferBO;

    }
    public PayRefund newRefundRecord(String param){
        RefundBO refundBO = new Gson().fromJson(param, RefundBO.class);

        PayRefund payRefund = new PayRefund();
        payRefund.setId(IdUtil.simpleUUID())
                .setRefundTradeNo("")
                .setStatus(PayRefundStatusEnum.REFUND_PROCESSING.name())
                .setStatusDesc("交易处理中")
                .setMessage(param)
                .setTradeNo(refundBO.getTradeNo())
                .setTradingOrderId(refundBO.getOutNo())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        payRefundMapper.insert(payRefund);
        return  payRefund;
    }
    public void modifyRefundRecordWhenRefundSuccess(PayRefund payRefund,String refundTradeNo){

        payRefund.setStatus(PayRefundStatusEnum.REFUND_SUCCESS.name())
                .setStatusDesc("退款成功")
                .setRefundTradeNo(refundTradeNo)
                .setModifiedAt(new Date());
        payRefundMapper.updateById(payRefund);

    }
}
