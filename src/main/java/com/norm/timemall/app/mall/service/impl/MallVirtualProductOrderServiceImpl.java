package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseSequenceMapper;
import com.norm.timemall.app.base.mo.CommonOrderPayment;
import com.norm.timemall.app.base.mo.VirtualOrder;
import com.norm.timemall.app.base.mo.VirtualProduct;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.mall.domain.dto.OrderVirtualProductDTO;
import com.norm.timemall.app.mall.mapper.CommonOrderPaymentMapper;
import com.norm.timemall.app.mall.mapper.MallVirtualOrderMapper;
import com.norm.timemall.app.mall.mapper.MallVirtualProductMapper;
import com.norm.timemall.app.mall.service.MallVirtualProductOrderService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class MallVirtualProductOrderServiceImpl implements MallVirtualProductOrderService {

    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private MallVirtualProductMapper mallVirtualProductMapper;
    @Autowired
    private MallVirtualOrderMapper mallVirtualOrderMapper;

    @Autowired
    private BaseSequenceMapper baseSequenceMapper;

    @Autowired
    private CommonOrderPaymentMapper commonOrderPaymentMapper;



    @Override
    public String newOrder(OrderVirtualProductDTO dto) {

        String buyerBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        VirtualProduct product = mallVirtualProductMapper.selectById(dto.getProductId());
        if(product==null){
            throw new QuickMessageException("未找到相关商品，下单失败");
        }
        if(!ProductStatusEnum.ONLINE.getMark().equals(product.getProductStatus())){
            throw new QuickMessageException("商品未处于在售状态，下单失败");
        }
        if(buyerBrandId.equals(product.getSellerBrandId())){
            throw new ErrorCodeException(CodeEnum.FALSE_SHOPPING);
        }
        // validated inventory
        if(product.getInventory() < dto.getQuantity()){
            throw new QuickMessageException("库存不足，下单失败");
        }
        // deduct inventory
        doDeductProductInventory(product.getId(), product.getInventory(), dto.getQuantity());

        // new order record
        Long no = baseSequenceMapper.nextSequence(SequenceKeyEnum.VIRTUAL_PRODUCT_ORDER_NO.getMark());
        String orderNO = "VO"+ RandomUtil.randomStringUpper(5)+no;
        BigDecimal total = product.getProductPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));

        String orderId= IdUtil.simpleUUID();
        VirtualOrder order = new VirtualOrder();
        order.setId(orderId)
             .setProductId(product.getId())
                .setOrderNo(orderNO)
                .setBuyerBrandId(buyerBrandId)
                .setProductPrice(product.getProductPrice())
                .setQuantity(dto.getQuantity())
                .setSellerBrandId(product.getSellerBrandId())
                .setTotalFee(total)
                .setTag(""+VirtualOrderTagEnum.WAITING_PAY.ordinal())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        mallVirtualOrderMapper.insert(order);

        // pay process
        doPayVirtualOrder(total,orderId);

        return orderId;

    }

    @Override
    public void repayOrder(String orderId) {

        VirtualOrder order = mallVirtualOrderMapper.selectById(orderId);

        if(order == null){
            throw new QuickMessageException("未找到相关订单，操作失败");
        }

        if(!SecurityUserHelper.getCurrentPrincipal().getBrandId().equals(order.getBuyerBrandId())){
            throw  new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        if(SwitchCheckEnum.ENABLE.getMark().equals(order.getAlreadyPay())){
            throw new QuickMessageException("订单已支付，请勿重复请求");
        }

        // pay process
        doPayVirtualOrder(order.getTotalFee(),order.getId());

    }

    private void doDeductProductInventory(String  productId,Integer inventory, Integer quantity){

        int inventoryGap=inventory - quantity;
        int latestInventory = Math.max(inventoryGap, 0);

        VirtualProduct product = new VirtualProduct();
        product.setId(productId);
        product.setInventory(latestInventory);
        product.setModifiedAt(new Date());

        mallVirtualProductMapper.updateById(product);


    }
    private void doPayVirtualOrder(BigDecimal revenue,String orderId){
        // pay
        if(revenue.compareTo(BigDecimal.ZERO)>0){
            TransferBO bo = generateTransferBO(revenue,orderId);
            String transNo=defaultPayService.transfer(new Gson().toJson(bo));
            // insert order payment
            newOrderPayment(orderId,transNo,revenue);
        }
        // update order tag,alreadyPay  as paid
        mallVirtualOrderMapper.updateTagAndPayById(SwitchCheckEnum.ENABLE.getMark(),VirtualOrderTagEnum.PAID.ordinal(),orderId);

    }
    private void newOrderPayment(String orderId, String tradeNo, BigDecimal total){

        CommonOrderPayment payment=new CommonOrderPayment();
        payment.setId(IdUtil.simpleUUID())
                .setTradingOrderId(orderId)
                .setTradingOrderType(PaymentOrderTypeEnum.VIRTUAL_PRODUCT_ORDER.name())
                .setTradeNo(tradeNo)
                .setStatus(""+PaymentStatusEnum.TRADE_SUCCESS.ordinal())
                .setStatusDesc(PaymentStatusEnum.TRADE_SUCCESS.name())
                .setPayType(""+PayType.BALANCE.getCode())
                .setPayTypeDesc(PayType.BALANCE.getDesc())
                .setTotalAmount(total)
                .setMessage("虚拟商品支付")
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        commonOrderPaymentMapper.insert(payment);

    }

    private TransferBO generateTransferBO(BigDecimal amount,String outNo){
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.OPERATOR.getMark())
                .setPayeeAccount(OperatorConfig.sysMidFinAccount)
                .setPayerAccount(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setPayerType(FidTypeEnum.BRAND.getMark())
                .setTransType(TransTypeEnum.VIRTUAL_PRODUCT_ORDER_PAY.getMark());
        return  bo;
    }
}
