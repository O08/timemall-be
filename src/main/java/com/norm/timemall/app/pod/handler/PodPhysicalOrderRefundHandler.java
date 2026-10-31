package com.norm.timemall.app.pod.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import cn.hutool.core.util.StrUtil;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CommonOrderPayment;
import com.norm.timemall.app.base.mo.PhysicalOrder;
import com.norm.timemall.app.base.mo.PhysicalOrderItem;
import com.norm.timemall.app.base.pojo.RefundBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.pod.domain.pojo.PodPhysicalOrderRefundHandlerParam;
import com.norm.timemall.app.pod.mapper.PodCommonOrderPaymentMapper;
import com.norm.timemall.app.pod.mapper.PodPhysicalOrderItemMapper;
import com.norm.timemall.app.pod.mapper.PodPhysicalOrderMapper;
import com.norm.timemall.app.pod.mapper.PodPhysicalProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
public class PodPhysicalOrderRefundHandler {

    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private PodPhysicalOrderMapper podPhysicalOrderMapper;
    @Autowired
    private PodPhysicalOrderItemMapper podPhysicalOrderItemMapper;
    @Autowired
    private PodCommonOrderPaymentMapper podCommonOrderPaymentMapper;

    @Autowired
    private PodPhysicalProductMapper physicalProductMapper;

    /**
     *  cancel order and refund to buyer
     *  */
    @Transactional(rollbackFor = Exception.class)
    public void doRefund(String param) {
        PodPhysicalOrderRefundHandlerParam handlerParam = new Gson().fromJson(param, PodPhysicalOrderRefundHandlerParam.class);
        if (handlerParam == null  || StrUtil.isBlank(handlerParam.getOrderId())) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if (handlerParam.getRefundReason() != null && handlerParam.getRefundReason().length() >= 100) {
            throw new QuickMessageException("退款原因长度必须小于100个字符");
        }

        // 锁住主订单，防止并发重复退款
        PhysicalOrder order = podPhysicalOrderMapper.selectForUpdate(handlerParam.getOrderId());
        if (order == null) {
            throw new QuickMessageException("未找到相关订单,操作失败");
        }

        // 验证用户
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (!currentUserBrandId.equals(order.getBuyerBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        if(PhysicalOrderPaymentStatusEnum.FULLY_REFUNDED.getMark().equals(order.getPaymentStatus())){
            throw new ErrorCodeException(CodeEnum.REFUND_REPEAT);
        }

        // 严格的支付状态幂等校验：只有已支付\未发货的订单才允许发起取消订单并进行退款
        if (!PhysicalOrderPaymentStatusEnum.PAID.getMark().equals(order.getPaymentStatus())) {
            throw new ErrorCodeException(CodeEnum.INVALID_STATUS);
        }

        if (!PhysicalOrderStatusEnum.PAID.getMark().equals(order.getOrderStatus())) {
            throw new ErrorCodeException(CodeEnum.INVALID_STATUS);
        }

        // 定位该实物订单成功的原始支付记录
        LambdaQueryWrapper<CommonOrderPayment> paymentWrapper = Wrappers.lambdaQuery();
        paymentWrapper.eq(CommonOrderPayment::getTradingOrderId, order.getId())
                .eq(CommonOrderPayment::getTradingOrderType, PaymentOrderTypeEnum.PHYSICAL_PRODUCT_ORDER.name())
                .eq(CommonOrderPayment::getStatus, String.valueOf(PaymentStatusEnum.TRADE_SUCCESS.ordinal()));
        paymentWrapper.last("LIMIT 1");
        CommonOrderPayment payment = podCommonOrderPaymentMapper.selectOne(paymentWrapper);
        if (payment == null) {
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }

        // 发起全额退款
        RefundBO bo = new RefundBO();
        bo.setOutNo(order.getId())
                .setTradeNo(payment.getTradeNo());
        defaultPayService.refund(new Gson().toJson(bo));

        Date now = new Date();

        // 更新所有子订单状态已退款
        podPhysicalOrderItemMapper.update(null,
                new com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper<PhysicalOrderItem>()
                        .eq(PhysicalOrderItem::getOrderId, order.getId())
                        .eq(PhysicalOrderItem::getStatus,PhysicalOrderItemStatusEnum.PAID.getMark()) // 只更新已支付的子订单
                        .set(PhysicalOrderItem::getStatus, PhysicalOrderItemStatusEnum.REFUNDED.getMark())
                        .set(PhysicalOrderItem::getRefundReason, handlerParam.getRefundReason())
                        .setSql("actual_refund_amount = split_pay_amount") // 数据库原生赋值，安全精准
                        .set(PhysicalOrderItem::getModifiedAt, now)
        );

        // 归还库存：买家取消购买，商品须立刻还回库存，防止“僵尸占位”
        List<PhysicalOrderItem> items = podPhysicalOrderItemMapper.selectList(
                Wrappers.<PhysicalOrderItem>lambdaQuery().eq(PhysicalOrderItem::getOrderId, order.getId())
        );
        for (PhysicalOrderItem item : items) {
            physicalProductMapper.restoreInventory(item.getProductId(), item.getQuantity(), now);
        }

        // 更新主订单状态为已退款
        PhysicalOrder updateOrder = new PhysicalOrder()
                .setId(order.getId())
                .setOrderStatus(PhysicalOrderStatusEnum.CANCELLED.getMark())
                .setPaymentStatus(PhysicalOrderPaymentStatusEnum.FULLY_REFUNDED.getMark()) // 资金状态：全额退款
                .setModifiedAt(now);
        podPhysicalOrderMapper.updateById(updateOrder);
    }
}
