package com.norm.timemall.app.pod.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CellPlanOrderTagEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.PaymentOrderTypeEnum;
import com.norm.timemall.app.base.enums.PaymentStatusEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CellPlanOrder;
import com.norm.timemall.app.base.mo.CommonOrderPayment;
import com.norm.timemall.app.base.pojo.RefundBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.pod.domain.pojo.PodCellPlanOrderRefundHandlerParam;
import com.norm.timemall.app.pod.mapper.PodCellPlanOrderMapper;
import com.norm.timemall.app.pod.mapper.PodCommonOrderPaymentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PodCellPlanOrderRefundHandler {
    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private PodCellPlanOrderMapper podCellPlanOrderMapper;
    @Autowired
    private PodCommonOrderPaymentMapper podCommonOrderPaymentMapper;
    public void doRefund(String param){

        PodCellPlanOrderRefundHandlerParam handlerParam = new Gson().fromJson(param, PodCellPlanOrderRefundHandlerParam.class);
        CellPlanOrder order = podCellPlanOrderMapper.selectById(handlerParam.getOrderId());

        LambdaQueryWrapper<CommonOrderPayment> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(CommonOrderPayment::getTradingOrderId,handlerParam.getOrderId())
                .eq(CommonOrderPayment::getTradingOrderType,PaymentOrderTypeEnum.CELL_PLAN_ORDER.name())
                .eq(CommonOrderPayment::getStatus,""+ PaymentStatusEnum.TRADE_SUCCESS.ordinal());
        wrapper.last("LIMIT 1");

        CommonOrderPayment payment = podCommonOrderPaymentMapper.selectOne(wrapper);
        // validate
        String userId= SecurityUserHelper.getCurrentPrincipal().getUserId();
        if(order==null || payment==null || !order.getConsumerId().equals(userId) || (order.getTag().equals(""+ CellPlanOrderTagEnum.COMPLETED.ordinal()))
            || (order.getTag().equals(""+ CellPlanOrderTagEnum.REFUNDED.ordinal()))
        ){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        RefundBO bo = new RefundBO();
        bo.setOutNo(handlerParam.getOrderId())
                .setTradeNo(payment.getTradeNo());

        defaultPayService.refund(new Gson().toJson(bo));

        // update order as refunded
        order.setTag(CellPlanOrderTagEnum.REFUNDED.ordinal()+"")
                .setModifiedAt(new Date());
        podCellPlanOrderMapper.updateById(order);

    }
}
