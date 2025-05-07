package com.norm.timemall.app.studio.handler;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseBluvarrierMapper;
import com.norm.timemall.app.base.mo.Bluvarrier;
import com.norm.timemall.app.base.mo.CommonOrderPayment;
import com.norm.timemall.app.base.mo.VirtualOrder;
import com.norm.timemall.app.base.pojo.RefundBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.studio.domain.pojo.StudioVirtualOrderRefundHandlerParam;
import com.norm.timemall.app.studio.mapper.StudioCommonOrderPaymentMapper;
import com.norm.timemall.app.studio.mapper.StudioVirtualOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

@Component
public class StudioVirtualProductOrderRefundHandler {

    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private StudioVirtualOrderMapper studioVirtualOrderMapper;

    @Autowired
    private StudioCommonOrderPaymentMapper studioCommonOrderPaymentMapper;

    @Autowired
    private BaseBluvarrierMapper baseBluvarrierMapper;

    public void doRefund(String param){
        StudioVirtualOrderRefundHandlerParam handlerParam = new Gson().fromJson(param,StudioVirtualOrderRefundHandlerParam.class);

        if(handlerParam==null || StrUtil.isBlank(handlerParam.getOrderId()) ){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(StrUtil.isBlank(handlerParam.getTerm()) || !"我支持退款".equals(handlerParam.getTerm())){
            throw new QuickMessageException("未进行退款授权,操作失败");
        }

        VirtualOrder order = studioVirtualOrderMapper.selectById(handlerParam.getOrderId());
        if(order ==null){
            throw new QuickMessageException("未找到相关订单,操作失败");
        }

        // role validated
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentUserBrandId.equals(order.getSellerBrandId());

        LambdaQueryWrapper<Bluvarrier> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark());
        Bluvarrier bluvarrier = baseBluvarrierMapper.selectOne(lambdaQueryWrapper);
        boolean isBluvarrier = ObjectUtil.isNotNull(bluvarrier) && currentUserBrandId.equals(bluvarrier.getBrandId());

        if(!(isSeller || isBluvarrier)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // refunded validated
        if(SwitchCheckEnum.ENABLE.getMark().equals(order.getAlreadyRefund())){
            throw new ErrorCodeException(CodeEnum.REPEAT_REQUEST_REFUND);
        }

        // remittance validated
        if(SwitchCheckEnum.ENABLE.getMark().equals(order.getAlreadyRemittance())){
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }

        if(order.getTotalFee().compareTo(BigDecimal.ZERO)==0){
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }

        if(!SwitchCheckEnum.ENABLE.getMark().equals(order.getAlreadyPay())){
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }

        LambdaQueryWrapper<CommonOrderPayment> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(CommonOrderPayment::getTradingOrderId,handlerParam.getOrderId())
                .eq(CommonOrderPayment::getTradingOrderType, PaymentOrderTypeEnum.VIRTUAL_PRODUCT_ORDER.name())
                .eq(CommonOrderPayment::getStatus,""+ PaymentStatusEnum.TRADE_SUCCESS.ordinal());
        wrapper.last("LIMIT 1");

        CommonOrderPayment payment = studioCommonOrderPaymentMapper.selectOne(wrapper);
        // validate have buy success history
        if(payment==null
        ){
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }

        RefundBO bo = new RefundBO();
        bo.setOutNo(handlerParam.getOrderId())
                .setTradeNo(payment.getTradeNo());

        defaultPayService.refund(new Gson().toJson(bo));

        // update order as refunded
        order.setTag(VirtualOrderTagEnum.REFUNDED.ordinal()+"");
        order.setModifiedAt(new Date());
        order.setAlreadyRefund(SwitchCheckEnum.ENABLE.getMark());

        studioVirtualOrderMapper.updateById(order);




    }
}
