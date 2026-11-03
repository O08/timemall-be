package com.norm.timemall.app.studio.handler;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CommonOrderPayment;
import com.norm.timemall.app.base.mo.VirtualOrder;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.studio.mapper.StudioCommonOrderPaymentMapper;
import com.norm.timemall.app.studio.mapper.StudioVirtualOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class StudioVirtualOrderRemittanceHandler {

    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private StudioVirtualOrderMapper studioVirtualOrderMapper;

    @Autowired
    private StudioCommonOrderPaymentMapper studioCommonOrderPaymentMapper;


    public void doRemittance(String orderId){


        VirtualOrder order = studioVirtualOrderMapper.selectById(orderId);
        if(order ==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // role validated
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentUserBrandId.equals(order.getSellerBrandId());

        if(!isSeller){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // validate time span >= 7 day
        if(new Date().getTime() < DateUtil.offsetDay(order.getCreateAt(),7).getTime()){
            throw new QuickMessageException("订单处在冷静期，打款功能在订单创建一周后激活，本次打款操作失败");
        }

        // remittance validated
        if(SwitchCheckEnum.ENABLE.getMark().equals(order.getAlreadyRemittance())){
            throw new QuickMessageException("已打款，请勿重复操作");
        }

        // refunded validated
        if(SwitchCheckEnum.ENABLE.getMark().equals(order.getAlreadyRefund())){
            throw new QuickMessageException("本单已进行退款处理，不支持汇款操作");
        }

        if(order.getTotalFee().compareTo(BigDecimal.ZERO)==0){
            throw new QuickMessageException("不支持操作-收付款为0");
        }


        LambdaQueryWrapper<CommonOrderPayment> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(CommonOrderPayment::getTradingOrderId,orderId)
                .eq(CommonOrderPayment::getTradingOrderType, PaymentOrderTypeEnum.VIRTUAL_PRODUCT_ORDER.name())
                .eq(CommonOrderPayment::getStatus,""+ PaymentStatusEnum.TRADE_SUCCESS.ordinal());
        wrapper.last("LIMIT 1");

        CommonOrderPayment payment = studioCommonOrderPaymentMapper.selectOne(wrapper);
        // validate have buy success history
        if(payment==null
        ){
            throw new QuickMessageException("不支持操作-交易合法性验证失败");
        }

        // pay to seller
        TransferBO bo = generateTransferBO(payment.getTotalAmount(), orderId);
        defaultPayService.transfer(new Gson().toJson(bo));

        // update order as remittance
        order.setTag(VirtualOrderTagEnum.REMITTANCE.ordinal()+"");
        order.setModifiedAt(new Date());
        order.setAlreadyRemittance(SwitchCheckEnum.ENABLE.getMark());

        studioVirtualOrderMapper.updateById(order);


    }

    private TransferBO generateTransferBO(BigDecimal amount, String outNo){
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setPayerAccount(OperatorConfig.sysMidFinAccount)
                .setPayerType(FidTypeEnum.OPERATOR.getMark())
                .setTransType(TransTypeEnum.VIRTUAL_REMITTANCE_TO_SELLER.getMark());

        return  bo;
    }
}
