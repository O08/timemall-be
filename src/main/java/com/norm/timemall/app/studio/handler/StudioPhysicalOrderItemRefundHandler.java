package com.norm.timemall.app.studio.handler;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseBluvarrierMapper;
import com.norm.timemall.app.base.mo.Bluvarrier;
import com.norm.timemall.app.base.mo.CommonOrderPayment;
import com.norm.timemall.app.base.mo.PhysicalOrder;
import com.norm.timemall.app.base.mo.PhysicalOrderItem;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.studio.domain.pojo.StudioPhysicalOrderItemRefundHandlerParam;
import com.norm.timemall.app.studio.mapper.StudioCommonOrderPaymentMapper;
import com.norm.timemall.app.studio.mapper.StudioPhysicalOrderItemMapper;
import com.norm.timemall.app.studio.mapper.StudioPhysicalOrderMapper;
import com.norm.timemall.app.studio.mapper.StudioPhysicalProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class StudioPhysicalOrderItemRefundHandler {

    @Autowired
    private DefaultPayService defaultPayService;

    @Autowired
    private StudioPhysicalProductMapper studioPhysicalProductMapper;
    @Autowired
    private StudioPhysicalOrderMapper studioPhysicalOrderMapper;
    @Autowired
    private StudioPhysicalOrderItemMapper studioPhysicalOrderItemMapper;
    @Autowired
    private StudioCommonOrderPaymentMapper studioCommonOrderPaymentMapper;
    @Autowired
    private BaseBluvarrierMapper baseBluvarrierMapper;

    @Transactional(rollbackFor = Exception.class)
    public void doRefund(String param) {
        StudioPhysicalOrderItemRefundHandlerParam handlerParam = new Gson().fromJson(param, StudioPhysicalOrderItemRefundHandlerParam.class);
        if (handlerParam == null || CharSequenceUtil.isBlank(handlerParam.getOrderItemId())) {
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if (CharSequenceUtil.isBlank(handlerParam.getTerm()) || !"我支持退款".equals(handlerParam.getTerm())) {
            throw new QuickMessageException("未进行退款授权,操作失败");
        }
        if (handlerParam.getRefundReason() != null && handlerParam.getRefundReason().length() >= 100) {
            throw new QuickMessageException("退款原因长度必须小于100个字符");
        }

        Date now = new Date();

        // 使用悲观锁 selectForUpdate 锁住这一行“子订单明细”，防止并发重复退款
        PhysicalOrderItem item = studioPhysicalOrderItemMapper.selectForUpdate(handlerParam.getOrderItemId());
        if (item == null) {
            throw new QuickMessageException("未找到相关订单商品,操作失败");
        }

        // 针对“子订单行”做防重复退款幂等校验
        if (PhysicalOrderItemStatusEnum.REFUNDED.getMark().equals(item.getStatus())) {
            throw new ErrorCodeException(CodeEnum.REPEAT_REQUEST_REFUND);
        }

        if (PhysicalOrderItemStatusEnum.SETTLED.getMark().equals(item.getStatus())) {
            throw new QuickMessageException("订单已结算, 操作失败");
        }
        if (!PhysicalOrderItemStatusEnum.PAID.getMark().equals(item.getStatus())) {
            throw new ErrorCodeException(CodeEnum.INVALID_STATUS);
        }


        // 锁住对应的主订单，确保整单财务安全
        PhysicalOrder mainOrder = studioPhysicalOrderMapper.selectForUpdate(item.getOrderId());

        validateMainOrderStatus(mainOrder);

        // 权限校验
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller = currentUserBrandId.equals(mainOrder.getSellerBrandId());

        LambdaQueryWrapper<Bluvarrier> bluvarrierWrapper = Wrappers.lambdaQuery();
        bluvarrierWrapper.eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark());
        Bluvarrier bluvarrier = baseBluvarrierMapper.selectOne(bluvarrierWrapper);
        boolean isBluvarrier = ObjectUtil.isNotNull(bluvarrier) && currentUserBrandId.equals(bluvarrier.getBrandId());

        if (!(isSeller || isBluvarrier)) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // 定位该订单成功的原始支付水流记录
        LambdaQueryWrapper<CommonOrderPayment> paymentWrapper = Wrappers.lambdaQuery();
        paymentWrapper.eq(CommonOrderPayment::getTradingOrderId, item.getOrderId())
                .eq(CommonOrderPayment::getTradingOrderType, PaymentOrderTypeEnum.PHYSICAL_PRODUCT_ORDER.name())
                .eq(CommonOrderPayment::getStatus, String.valueOf(PaymentStatusEnum.TRADE_SUCCESS.ordinal()));
        paymentWrapper.last("LIMIT 1");
        CommonOrderPayment payment = studioCommonOrderPaymentMapper.selectOne(paymentWrapper);
        if (payment == null) {
            throw new ErrorCodeException(CodeEnum.UN_SUPPORT_REFUND_ACTION);
        }

        // 子订单部分退款：将平台中间账户中的该商品金额原路退回给买家
        refundItemToBuyer(item, mainOrder);

        // 更新子订单状态为已退款
        item.setStatus(PhysicalOrderItemStatusEnum.REFUNDED.getMark());
        item.setRefundReason(handlerParam.getRefundReason());
        item.setActualRefundAmount(item.getSplitPayAmount());
        item.setModifiedAt(now);
        studioPhysicalOrderItemMapper.updateById(item);

        // 退款返还库存：把该子单占用的库存加回商品池
        studioPhysicalProductMapper.restoreInventory(item.getProductId(), item.getQuantity(), now);

        // 根据同单兄弟商品的状态，反向刷新主订单的生命周期和资金状态
        this.syncParentOrderStatusAfterStockout(mainOrder, now);
    }

    private void validateMainOrderStatus(PhysicalOrder mainOrder) {
        if (mainOrder == null) {
            throw new QuickMessageException("未找到相关订单，操作失败");
        }

        // 订单状态校验
        if(PhysicalOrderStatusEnum.PAID.getMark().equals(mainOrder.getOrderStatus())){
            throw new QuickMessageException("已支付未发货的订单，仅支持买家自主退款，商家侧不开放");
        }
        boolean mainOrderSupportRefund = PhysicalOrderStatusEnum.SHIPPED.getMark().equals(mainOrder.getOrderStatus())
                || PhysicalOrderStatusEnum.COMPLETED.getMark().equals(mainOrder.getOrderStatus());

        if(!mainOrderSupportRefund){
            throw new QuickMessageException("该订单不支持退款，操作失败");
        }
        // 资金状态校验
        if (PhysicalOrderPaymentStatusEnum.ALREADY_REMITTANCE.getMark().equals(mainOrder.getPaymentStatus())) {
            throw new QuickMessageException("该订单货款已结算完成，操作失败");
        }
        if (PhysicalOrderPaymentStatusEnum.REMITTING.getMark().equals(mainOrder.getPaymentStatus())) {
            throw new QuickMessageException("货款结算中，操作失败");
        }
        if(PhysicalOrderPaymentStatusEnum.FULLY_REFUNDED.getMark().equals(mainOrder.getPaymentStatus())){
            throw new ErrorCodeException(CodeEnum.REFUND_REPEAT);
        }
        boolean supportRefundPaymentStatus = PhysicalOrderPaymentStatusEnum.PAID.getMark().equals(mainOrder.getPaymentStatus())
                || PhysicalOrderPaymentStatusEnum.PARTIAL_REFUNDED.getMark().equals(mainOrder.getPaymentStatus());

        if(!supportRefundPaymentStatus){
            throw new QuickMessageException("资金状态验证不通过，操作失败");
        }
    }


    private void syncParentOrderStatusAfterStockout(PhysicalOrder parentOrder, Date now) {

        // 查出该订单下所有的商品明细条数
        List<PhysicalOrderItem> allItems = studioPhysicalOrderItemMapper.selectList(
                Wrappers.<PhysicalOrderItem>lambdaQuery().eq(PhysicalOrderItem::getOrderId, parentOrder.getId())
        );

        long totalCount = allItems.size();
        // 统计当前已经变成已退款的子单有多少个
        long refundedCount = allItems.stream()
                .filter(i -> PhysicalOrderItemStatusEnum.REFUNDED.getMark().equals(i.getStatus()))
                .count();

        PhysicalOrder updateMain = new PhysicalOrder().setId(parentOrder.getId()).setModifiedAt(now);

        if (refundedCount == totalCount) {
            updateMain.setOrderStatus(PhysicalOrderStatusEnum.REFUNDED.getMark()); // 已退款/已关闭
            updateMain.setPaymentStatus(PhysicalOrderPaymentStatusEnum.FULLY_REFUNDED.getMark()); // 全额退款
        } else {
            // 标准场景：仍有其他有货商品需要继续配送
            updateMain.setPaymentStatus(PhysicalOrderPaymentStatusEnum.PARTIAL_REFUNDED.getMark());
        }

        studioPhysicalOrderMapper.updateById(updateMain);
    }


    private void refundItemToBuyer(PhysicalOrderItem item, PhysicalOrder parentOrder) {
        BigDecimal refundAmount = item.getSplitPayAmount();
        if (refundAmount == null || refundAmount.compareTo(BigDecimal.ZERO) <= 0) {
            return;
        }

        TransferBO bo = new TransferBO();
        bo.setAmount(refundAmount)
                .setOutNo(item.getId())
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(parentOrder.getBuyerBrandId())
                .setPayerType(FidTypeEnum.OPERATOR.getMark())
                .setPayerAccount(OperatorConfig.sysMidFinAccount)
                .setTransType(TransTypeEnum.PHYSICAL_ORDER_ITEM_REFUND.getMark());
        defaultPayService.transfer(new Gson().toJson(bo));
    }

}
