package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseBrandMapper;
import com.norm.timemall.app.base.mapper.BaseBrandSubMapper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.mall.domain.dto.MallOrderPhysicalProductDTO;
import com.norm.timemall.app.mall.domain.pojo.MallOrderPhysicalProductItem;
import com.norm.timemall.app.mall.domain.ro.MallPhysicalOrderDetailRO;
import com.norm.timemall.app.mall.mapper.CommonOrderPaymentMapper;
import com.norm.timemall.app.mall.mapper.MallPhysicalOrderItemMapper;
import com.norm.timemall.app.mall.mapper.MallPhysicalProductMapper;
import com.norm.timemall.app.mall.mapper.MallPodPhysicalOrderMapper;
import com.norm.timemall.app.mall.service.MallPhysicalOrderService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MallPhysicalOrderServiceImpl implements MallPhysicalOrderService {
    @Autowired
    private MallPodPhysicalOrderMapper podPhysicalOrderMapper;
    @Autowired
    private MallPhysicalOrderItemMapper physicalOrderItemMapper;
    @Autowired
    private CommonOrderPaymentMapper commonOrderPaymentMapper;
    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private MallPhysicalProductMapper physicalProductMapper;

    @Autowired
    private BaseBrandSubMapper baseBrandSubMapper;
    
    @Autowired
    private BaseBrandMapper baseBrandMapper;

    private static final BigDecimal MAX_ORDER_AMOUNT = new BigDecimal("999999.99");


    @Override
    @Transactional(rollbackFor = Exception.class)
    public String newOrder(MallOrderPhysicalProductDTO dto) {

        // 1. Global boundary check
        MallOrderPhysicalProductItem[] items = dto.getItems();
        if (items == null || items.length == 0) {
            throw new QuickMessageException("您的购物车内还没有商品，请先挑选商品再结算吧~");
        }
        long uniqueProductCount = Arrays.stream(items)
                .map(MallOrderPhysicalProductItem::getProductId)
                .filter(StrUtil::isNotBlank)
                .distinct()
                .count();

        if (uniqueProductCount != items.length) {
            throw new QuickMessageException("提交的结算清单中包含重复商品，请刷新购物车后重试");
        }


        String buyerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Date now = new Date();
        String orderId = IdUtil.simpleUUID();

        BigDecimal totalAmount = BigDecimal.ZERO;
        String sellerBrandId = null;
        List<PhysicalProduct> verifiedProducts = new ArrayList<>();

        // 2. Iterate and process items through the validation holder
        for (MallOrderPhysicalProductItem item : items) {
            PhysicalProduct product = this.pureValidateItem(item);

            // 校验跨商家逻辑
            if (sellerBrandId == null) {
                sellerBrandId = product.getSellerBrandId();
            } else if (!sellerBrandId.equals(product.getSellerBrandId())) {
                throw new QuickMessageException("一次下单仅支持同一商家的商品");
            }
            // 累加计算价格
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).setScale(2, RoundingMode.HALF_UP);
            totalAmount = totalAmount.add(itemTotal);

            // 暂存商品信息
            verifiedProducts.add(product);
        }
        if(buyerBrandId.equals(sellerBrandId)){
            throw new ErrorCodeException(CodeEnum.FALSE_SHOPPING);
        }
        if(totalAmount.compareTo(BigDecimal.ZERO)<=0){
            throw new QuickMessageException("商品价格异常，请刷新购物车后重试");
        }
        if (totalAmount.compareTo(MAX_ORDER_AMOUNT) > 0) {
            throw new QuickMessageException("订单总额已超过单笔交易上限  " + MAX_ORDER_AMOUNT);
        }
        Brand sellerInfo =  baseBrandMapper.selectById(sellerBrandId);
        if(BrandMarkEnum.CLOSED.getMark().equals(sellerInfo.getMark())){
            throw new QuickMessageException("商家账号异常");
        }
        BrandSub sellerStoreInfo = baseBrandSubMapper.selectByBrandId(sellerBrandId);
        boolean noFoundDeliveryConfig = ObjectUtil.isNull(sellerStoreInfo) || ObjectUtil.isNull(sellerStoreInfo.getMinDeliveryAmount());
        BigDecimal minDeliveryAmount = noFoundDeliveryConfig  ?  BigDecimal.valueOf(20) : sellerStoreInfo.getMinDeliveryAmount();

        if (!isMinDeliveryAmountMet(totalAmount, minDeliveryAmount)) {
            throw new QuickMessageException("商品金额未达到起送费要求（满 " + minDeliveryAmount.toPlainString() + " 元起送）");
        }

        Map<String, String> productNameMap = verifiedProducts.stream()
                .collect(Collectors.toMap(PhysicalProduct::getId, PhysicalProduct::getProductName));

        // 利用 Java Stream 按照 ProductId 字符串的字典序进行严格排序
        List<MallOrderPhysicalProductItem> sortedItems = Arrays.stream(items)
                .sorted(Comparator.comparing(MallOrderPhysicalProductItem::getProductId))
                .toList();

        // 严格按照排序后的顺序依次加锁扣减库存，彻底破坏“交叉等待”的死锁条件！
        for (MallOrderPhysicalProductItem item : sortedItems) {
            int updateRows = physicalProductMapper.deductInventory(item.getProductId(), item.getQuantity(), now);
            if (updateRows == 0) {
                String productName = productNameMap.getOrDefault(item.getProductId(), "未知商品");
                throw new QuickMessageException("很抱歉，商品 [" + productName + "] 在结算瞬间被抢光，库存不足");
            }
        }

        BigDecimal promotionAmount = BigDecimal.ZERO;
        BigDecimal payAmount = totalAmount.subtract(promotionAmount);
        List<PhysicalOrderItem> orderItems = buildPhysicalOrderItems(items,orderId,totalAmount,verifiedProducts,promotionAmount);

        PhysicalOrder order = buildPhysicalOrder(dto, orderId, buyerBrandId, sellerBrandId, totalAmount, promotionAmount, payAmount, now);
        podPhysicalOrderMapper.insert(order);

        // Batch write child orders
        for (PhysicalOrderItem orderItem : orderItems) {
            physicalOrderItemMapper.insert(orderItem);
        }

        return orderId;
    }

    boolean isMinDeliveryAmountMet(BigDecimal orderAmount, BigDecimal minDeliveryAmount) {
        if (orderAmount == null || minDeliveryAmount == null) {
            throw new QuickMessageException("订单金额或起送费为空");
        }

        return orderAmount.compareTo(minDeliveryAmount) >= 0;
    }

    private List<PhysicalOrderItem> buildPhysicalOrderItems(MallOrderPhysicalProductItem[] items,String orderId,BigDecimal totalAmount,List<PhysicalProduct> verifiedProducts,BigDecimal promotionAmount) {
        BigDecimal accumulatedSplitPayAmount = BigDecimal.ZERO;
        BigDecimal accumulatedSplitPromotionAmount = BigDecimal.ZERO;
        BigDecimal payAmount = totalAmount.subtract(promotionAmount);
        List<PhysicalOrderItem> orderItems = new ArrayList<>();
        Date now = new Date();

        // 生成子订单时，依然使用前端的 items 顺序，确保跟买家界面的账单列表顺序完全一致
        for (int i = 0; i < items.length; i++) {
            MallOrderPhysicalProductItem item = items[i];
            PhysicalProduct product = verifiedProducts.get(i);
            BigDecimal itemTotalOriginal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())).setScale(2, RoundingMode.HALF_UP);

            BigDecimal splitItemPromotion;
            BigDecimal splitItemPay;

            // Handle allocation pricing and flat adjustments cleanly here
            if (i == items.length - 1) {
                splitItemPromotion = promotionAmount.subtract(accumulatedSplitPromotionAmount);
                splitItemPay = payAmount.subtract(accumulatedSplitPayAmount);
            } else {
                splitItemPromotion = promotionAmount.multiply(itemTotalOriginal)
                        .divide(totalAmount, 2, RoundingMode.DOWN);
                splitItemPay = itemTotalOriginal.subtract(splitItemPromotion);

                accumulatedSplitPromotionAmount = accumulatedSplitPromotionAmount.add(splitItemPromotion);
                accumulatedSplitPayAmount = accumulatedSplitPayAmount.add(splitItemPay);
            }

            // Map and collect sub-order item details
            PhysicalOrderItem orderItem = new PhysicalOrderItem()
                    .setId(IdUtil.simpleUUID())
                    .setOrderId(orderId)
                    .setProductId(product.getId())
                    .setProductName(product.getProductName())
                    .setProductThumbnail(product.getThumbnail())
                    .setProductPrice(product.getPrice())
                    .setQuantity(item.getQuantity())
                    .setSplitPromotionAmount(splitItemPromotion)
                    .setSplitPayAmount(splitItemPay)
                    .setStatus(PhysicalOrderItemStatusEnum.PENDING_PAYMENT.getMark())
                    .setCreateAt(now)
                    .setModifiedAt(now);
            orderItems.add(orderItem);
        }
        return orderItems;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void executePayment(String orderId) {
        // 使用悲观锁 FOR UPDATE 锁住这笔订单，防止并发冲突
        PhysicalOrder order = podPhysicalOrderMapper.selectForUpdate(orderId);
        if (order == null) {
            throw new QuickMessageException("未找到相关订单");
        }

        //  幂等性检查：防止二次扣款资损
        if (!PhysicalOrderPaymentStatusEnum.UNPAID.getMark().equals(order.getPaymentStatus())) {
            throw new ErrorCodeException(CodeEnum.INVALID_STATUS);
        }

        if (!PhysicalOrderStatusEnum.PENDING_PAYMENT.getMark().equals(order.getOrderStatus())) {
            throw new ErrorCodeException(CodeEnum.INVALID_STATUS);
        }

        // 仅买家本人可发起支付
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (!currentUserBrandId.equals(order.getBuyerBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        BigDecimal payAmount = order.getPayAmount();

        if (payAmount != null && payAmount.compareTo(BigDecimal.ZERO) > 0) {
            String tradeNo = transferToPlatform(payAmount, order.getId(), order.getBuyerBrandId());
            // 记录扣款流水
            newOrderPayment(order.getId(), tradeNo, payAmount);
        }

        Date updateTime = new Date();

        // 批量更新该订单下所有子订单（明细）的状态为已支付
        physicalOrderItemMapper.update(null,
                new LambdaUpdateWrapper<PhysicalOrderItem>()
                        .eq(PhysicalOrderItem::getOrderId, orderId)
                        .set(PhysicalOrderItem::getStatus, PhysicalOrderItemStatusEnum.PAID.getMark())
                        .set(PhysicalOrderItem::getModifiedAt, updateTime)
        );

        // update soldOrders
        List<PhysicalOrderItem> allItems = physicalOrderItemMapper.selectList(
                Wrappers.<PhysicalOrderItem>lambdaQuery().eq(PhysicalOrderItem::getOrderId, orderId)
        );
        // 遍历子订单，原子累加每件实物商品的销量
        for (PhysicalOrderItem item : allItems) {
            physicalProductMapper.incrementSalesVolume(item.getProductId(), item.getQuantity(), updateTime);
        }

        // 扣款成功，翻转主订单和支付状态
        PhysicalOrder update = new PhysicalOrder()
                .setId(order.getId())
                .setOrderStatus(PhysicalOrderStatusEnum.PAID.getMark())
                .setShippingStatus(PhysicalOrderShippingStatusEnum.PENDING.getMark())
                .setPaymentStatus(PhysicalOrderPaymentStatusEnum.PAID.getMark())
                .setModifiedAt(new Date());
        podPhysicalOrderMapper.updateById(update);
    }

    private PhysicalProduct pureValidateItem(MallOrderPhysicalProductItem item) {
        if (item.getQuantity() == null || item.getQuantity() <= 0) {
            throw new QuickMessageException("购买数量不合法");
        }

        PhysicalProduct product = physicalProductMapper.selectById(item.getProductId());
        if (product == null) {
            throw new QuickMessageException("商品 ["+item.getProductId() + "] 数据未发现");
        }
        if (!ProductStatusEnum.ONLINE.getMark().equals(product.getStatus())) {
            throw new QuickMessageException("商品 [" + product.getProductName() + "] 未上架售卖，无法下单");
        }
        if (product.getInventory() == null || product.getInventory() < item.getQuantity()) {
            throw new QuickMessageException("商品 [" + product.getProductName() + "] 库存不足");
        }
        if (product.getPrice() == null || product.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            throw new QuickMessageException("商品 [" + product.getProductName() + "] 价格异常，无法下单");
        }

        return product;
    }

    private PhysicalOrder buildPhysicalOrder(MallOrderPhysicalProductDTO dto, String orderId, String buyerBrandId,
                                             String sellerBrandId, BigDecimal totalAmount, BigDecimal promotionAmount,
                                             BigDecimal payAmount, Date now) {
        return new PhysicalOrder()
                .setId(orderId)
                .setBuyerBrandId(buyerBrandId)
                .setSellerBrandId(sellerBrandId)
                .setTotalAmount(totalAmount)
                .setPromotionAmount(promotionAmount)
                .setPayAmount(payAmount)
                .setRecipient(dto.getRecipient())
                .setRecipientPhone(dto.getRecipientPhone())
                .setShippingAddress(dto.getShippingAddress())
                .setOrderStatus(PhysicalOrderStatusEnum.PENDING_PAYMENT.getMark())
                .setPaymentStatus(PhysicalOrderPaymentStatusEnum.UNPAID.getMark())
                .setCreateAt(now)
                .setModifiedAt(now);
    }


    private String transferToPlatform(BigDecimal amount, String outNo, String payerBrandId) {
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.OPERATOR.getMark())
                .setPayeeAccount(OperatorConfig.sysMidFinAccount)
                .setPayerType(FidTypeEnum.BRAND.getMark())
                .setPayerAccount(payerBrandId)
                .setTransType(TransTypeEnum.PHYSICAL_ORDER_PAY.getMark());
        return defaultPayService.transfer(new Gson().toJson(bo));
    }


    @Override
    public MallPhysicalOrderDetailRO findOrderDetail(String orderId) {
        if (StrUtil.isEmpty(orderId)) {
            return null;
        }
        PhysicalOrder order = podPhysicalOrderMapper.selectById(orderId);
        if (order == null) {
            return null;
        }
        List<PhysicalOrderItem> items = physicalOrderItemMapper.selectList(
                Wrappers.<PhysicalOrderItem>lambdaQuery().eq(PhysicalOrderItem::getOrderId, orderId)
        );
        MallPhysicalOrderDetailRO ro = new MallPhysicalOrderDetailRO();
        ro.setOrder(order);
        ro.setItems(items);
        return ro;
    }

    private void newOrderPayment(String orderId, String tradeNo, BigDecimal total) {
        Date now = new Date();
        CommonOrderPayment payment = new CommonOrderPayment();
        payment.setId(IdUtil.simpleUUID())
                .setTradingOrderId(orderId)
                .setTradingOrderType(PaymentOrderTypeEnum.PHYSICAL_PRODUCT_ORDER.name())
                .setTradeNo(tradeNo)
                .setStatus("" + PaymentStatusEnum.TRADE_SUCCESS.ordinal())
                .setStatusDesc(PaymentStatusEnum.TRADE_SUCCESS.name())
                .setPayType("" + PayType.BALANCE.getCode())
                .setPayTypeDesc(PayType.BALANCE.getDesc())
                .setTotalAmount(total)
                .setMessage("实物商品支付")
                .setCreateAt(now)
                .setModifiedAt(now);
        commonOrderPaymentMapper.insert(payment);
    }


}
