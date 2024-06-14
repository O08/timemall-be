package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Cell;
import com.norm.timemall.app.base.mo.CellPlan;
import com.norm.timemall.app.base.mo.CellPlanOrder;
import com.norm.timemall.app.base.mo.CommonOrderPayment;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.mall.domain.dto.AffiliateDTO;
import com.norm.timemall.app.mall.domain.ro.CellPlanOrderRO;
import com.norm.timemall.app.mall.mapper.CellMapper;
import com.norm.timemall.app.mall.mapper.CellPlanMapper;
import com.norm.timemall.app.mall.mapper.CellPlanOrderMapper;
import com.norm.timemall.app.mall.mapper.CommonOrderPaymentMapper;
import com.norm.timemall.app.mall.service.CellPlanOrderService;
import com.norm.timemall.app.mall.service.MallAffiliateOrderService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class CellPlanOrderServiceImpl implements CellPlanOrderService {
    @Autowired
    private CellPlanOrderMapper cellPlanOrderMapper;

    @Autowired
    private CellMapper cellMapper;
    @Autowired
    private CellPlanMapper cellPlanMapper;
    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private CommonOrderPaymentMapper commonOrderPaymentMapper;
    @Autowired
    private MallAffiliateOrderService mallAffiliateOrderService;
    @Override
    public String newOrder(String planId, AffiliateDTO dto) {

        CellPlan plan = cellPlanMapper.selectById(planId);
        if(plan==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        Cell cell = cellMapper.selectById(plan.getCellId());
        if(cell==null || SecurityUserHelper.getCurrentPrincipal().getBrandId().equals(cell.getBrandId())){
            throw new ErrorCodeException(CodeEnum.FALSE_SHOPPING);
        }
        String orderId=IdUtil.simpleUUID();
        CellPlanOrder order =new CellPlanOrder();
        order.setId(orderId)
                .setCellId(plan.getCellId())
                .setPlanId(planId)
                .setPlanTitle(plan.getTitle())
                .setPlanContent(plan.getContent())
                .setPlanFeature(plan.getFeature())
                .setPlanType(plan.getPlanType())
                .setPlanTypeDesc(plan.getPlanTypeDesc())
                .setPlanPrice(plan.getPrice())
                .setTag(""+CellPlanOrderTagEnum.WAITING_PAY.ordinal())
                .setConsumerId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        cellPlanOrderMapper.insert(order);

        // pay
        TransferBO bo = generateTransferBO(plan.getPrice(),orderId);
        String transNo=defaultPayService.transfer(new Gson().toJson(bo));

        // insert order payment
        newOrderPayment(orderId,transNo,plan.getPrice());
        // update order tag as paid
        cellPlanOrderMapper.updateTagById(CellPlanOrderTagEnum.PAID.ordinal(),orderId);
        // affiliate order
        mallAffiliateOrderService.newAffiliateOrder(plan.getCellId(),orderId,AffiliateOrderTypeEnum.PLAN.getMark(),
                plan.getPrice(),dto.getInfluencer(),dto.getChn(),dto.getMarket());
        return orderId;

    }

    @Override
    public CellPlanOrderRO findOrder(String orderId) {
        return cellPlanOrderMapper.selectCellPlanOrderROById(orderId);
    }

    private TransferBO generateTransferBO(BigDecimal amount,String outNo){
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.OPERATOR.getMark())
                .setPayeeAccount(OperatorConfig.sysCellPlanOrderMidFinAccount)
                .setPayerAccount(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setPayerType(FidTypeEnum.BRAND.getMark())
                .setTransType(TransTypeEnum.PLAN_ORDER_PAY.getMark());
        return  bo;
    }
    private void newOrderPayment(String orderId, String tradeNo, BigDecimal total){

        CommonOrderPayment payment=new CommonOrderPayment();
        payment.setId(IdUtil.simpleUUID())
                .setTradingOrderId(orderId)
                .setTradingOrderType(PaymentOrderTypeEnum.CELL_PLAN_ORDER.name())
                .setTradeNo(tradeNo)
                .setStatus(""+PaymentStatusEnum.TRADE_SUCCESS.ordinal())
                .setStatusDesc(PaymentStatusEnum.TRADE_SUCCESS.name())
                .setPayType(""+PayType.BALANCE.getCode())
                .setPayTypeDesc(PayType.BALANCE.getDesc())
                .setTotalAmount(total)
                .setMessage("单品支付")
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        commonOrderPaymentMapper.insert(payment);

    }
}
