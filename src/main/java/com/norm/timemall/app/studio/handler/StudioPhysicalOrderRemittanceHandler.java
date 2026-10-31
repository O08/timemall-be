package com.norm.timemall.app.studio.handler;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CommonOrderPayment;
import com.norm.timemall.app.base.mo.PayTransfer;
import com.norm.timemall.app.base.mo.PhysicalOrder;
import com.norm.timemall.app.base.mo.PhysicalOrderItem;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.mapper.PayTransferMapper;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.studio.mapper.StudioCommonOrderPaymentMapper;
import com.norm.timemall.app.studio.mapper.StudioPhysicalOrderItemMapper;
import com.norm.timemall.app.studio.mapper.StudioPhysicalOrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Component
public class StudioPhysicalOrderRemittanceHandler {

    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private StudioPhysicalOrderMapper studioPhysicalOrderMapper;
    @Autowired
    private StudioPhysicalOrderItemMapper studioPhysicalOrderItemMapper;
    @Autowired
    private StudioCommonOrderPaymentMapper studioCommonOrderPaymentMapper;



    @Transactional(rollbackFor = Exception.class)
    public void doRemittance(String orderId) {

        PhysicalOrder order = studioPhysicalOrderMapper.selectForUpdate(orderId);
        if (order == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        // Role validation
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (!currentUserBrandId.equals(order.getSellerBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // 操作细化提示
        if(PhysicalOrderPaymentStatusEnum.FULLY_REFUNDED.getMark().equals(order.getPaymentStatus())){
            throw new QuickMessageException("该订单已退款");
        }
        if (PhysicalOrderPaymentStatusEnum.ALREADY_REMITTANCE.getMark().equals(order.getPaymentStatus())) {
            throw new QuickMessageException("该订单货款已结算完成，请勿重复操作");
        }
        if (PhysicalOrderPaymentStatusEnum.REMITTING.getMark().equals(order.getPaymentStatus())) {
            throw new QuickMessageException("货款结算中...");
        }

        // Cooling-off verification
        if (new Date().getTime() < DateUtil.offsetDay(order.getCreateAt(), 7).getTime()) {
            throw new QuickMessageException("订单处在冷静期，结算功能在订单创建一周后激活，操作失败");
        }

        // 仅完成交付的订单，才能进入收款流程
        if (!PhysicalOrderStatusEnum.COMPLETED.getMark().equals(order.getOrderStatus())) {
            throw new ErrorCodeException(CodeEnum.INVALID_STATUS);
        }


        boolean supportRemittance=PhysicalOrderPaymentStatusEnum.PAID.getMark().equals(order.getPaymentStatus())
                  || PhysicalOrderPaymentStatusEnum.PARTIAL_REFUNDED.getMark().equals(order.getPaymentStatus());

        if(!supportRemittance){
            throw new QuickMessageException("该订单不支持收款");
        }

        List<PhysicalOrderItem> activeItems = studioPhysicalOrderItemMapper.selectList(
                Wrappers.<PhysicalOrderItem>lambdaQuery()
                        .eq(PhysicalOrderItem::getOrderId, orderId)
                        .eq(PhysicalOrderItem::getStatus, PhysicalOrderItemStatusEnum.PAID.getMark())
        );

        BigDecimal payable = activeItems.stream()
                .map(PhysicalOrderItem::getSplitPayAmount)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (payable.compareTo(BigDecimal.ZERO) <= 0) {
            throw new QuickMessageException("不支持操作-扣除退款后可清算金额为0");
        }

        // Locate original transaction log
        LambdaQueryWrapper<CommonOrderPayment> paymentWrapper = Wrappers.lambdaQuery();
        paymentWrapper.eq(CommonOrderPayment::getTradingOrderId, orderId)
                .eq(CommonOrderPayment::getTradingOrderType, PaymentOrderTypeEnum.PHYSICAL_PRODUCT_ORDER.name())
                .eq(CommonOrderPayment::getStatus, String.valueOf(PaymentStatusEnum.TRADE_SUCCESS.ordinal()));
        paymentWrapper.last("LIMIT 1");
        CommonOrderPayment payment = studioCommonOrderPaymentMapper.selectOne(paymentWrapper);
        if (payment == null) {
            throw new QuickMessageException("不支持操作-交易合法性验证失败");
        }

        // 修改状态为结算中
        PhysicalOrder updateOrder = new PhysicalOrder()
                .setId(order.getId())
                .setPaymentStatus(PhysicalOrderPaymentStatusEnum.REMITTING.getMark())
                .setModifiedAt(new Date());
        studioPhysicalOrderMapper.updateById(updateOrder);

        //pay seller
        TransferBO bo = generateTransferBO(payable, orderId, order.getSellerBrandId());
        defaultPayService.transfer(new Gson().toJson(bo));

        // 批量更新该订单下所有子订单（明细）的状态为已结算
        studioPhysicalOrderItemMapper.update(null,
                new LambdaUpdateWrapper<PhysicalOrderItem>()
                        .eq(PhysicalOrderItem::getOrderId, orderId)
                        .eq(PhysicalOrderItem::getStatus, PhysicalOrderItemStatusEnum.PAID.getMark())
                        .set(PhysicalOrderItem::getStatus, PhysicalOrderItemStatusEnum.SETTLED.getMark())
                        .set(PhysicalOrderItem::getModifiedAt, new Date())
        );

        PhysicalOrder finishPayUpdateOrder = new PhysicalOrder()
                .setId(order.getId())
                .setPaymentStatus(PhysicalOrderPaymentStatusEnum.ALREADY_REMITTANCE.getMark()) // Bring lifecycle to a close
                .setModifiedAt(new Date());
        studioPhysicalOrderMapper.updateById(finishPayUpdateOrder);
    }


    private TransferBO generateTransferBO(BigDecimal amount, String outNo, String sellerBrandId) {
        return new TransferBO()
                .setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(sellerBrandId)
                .setPayerAccount(OperatorConfig.sysMidFinAccount)
                .setPayerType(FidTypeEnum.OPERATOR.getMark())
                .setTransType(TransTypeEnum.PHYSICAL_REMITTANCE_TO_SELLER.getMark());
    }
}
