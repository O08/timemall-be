package com.norm.timemall.app.mall.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.mall.domain.dto.AffiliateDTO;
import com.norm.timemall.app.mall.domain.dto.MallFetchPromotionInfoDTO;
import com.norm.timemall.app.mall.domain.ro.CellPlanOrderRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionBenefitRO;
import com.norm.timemall.app.mall.domain.ro.MallFetchPromotionInfoRO;
import com.norm.timemall.app.mall.mapper.*;
import com.norm.timemall.app.mall.service.CellPlanOrderService;
import com.norm.timemall.app.mall.service.MallAffiliateOrderService;
import com.norm.timemall.app.mall.service.MallBrandPromotionService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    @Autowired
    private MallBrandPromotionService mallBrandPromotionService;
    @Autowired
    private MallOrderCouponMapper mallOrderCouponMapper;

    @Autowired
    private MallCreditCouponMapper mallCreditCouponMapper;

    @Autowired
    private MallBrandPromotionMapper mallBrandPromotionMapper;

    @Override
    public String newOrder(String planId, AffiliateDTO dto) {

        CellPlan plan = cellPlanMapper.selectById(planId);
        if(plan==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        Cell cell = cellMapper.selectById(plan.getCellId());
        if(cell==null || !CellMarkEnum.ONLINE.getMark().equals(cell.getMark()) ){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(SecurityUserHelper.getCurrentPrincipal().getBrandId().equals(cell.getBrandId())){
            throw new ErrorCodeException(CodeEnum.FALSE_SHOPPING);
        }
        String orderId=IdUtil.simpleUUID();
        BigDecimal promotionDeduction=calPromotionDeduction(cell.getId(),cell.getBrandId(),plan.getPrice(),orderId);
        BigDecimal revenue=plan.getPrice().subtract(promotionDeduction);
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
                .setRevenue(revenue)
                .setPromotionDeduction(promotionDeduction)
                .setTag(""+CellPlanOrderTagEnum.WAITING_PAY.ordinal())
                .setBrandId(cell.getBrandId())
                .setConsumerId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        cellPlanOrderMapper.insert(order);

        // pay
        if(revenue.compareTo(BigDecimal.ZERO)>0){
            TransferBO bo = generateTransferBO(revenue,orderId);
            String transNo=defaultPayService.transfer(new Gson().toJson(bo));
            // insert order payment
            newOrderPayment(orderId,transNo,revenue);
        }

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

    /***
     * note：cnt 在并发大大时候可能不准
     * @param cellId
     * @param supplierBrandId
     * @param total
     * @param orderId
     * @return
     */
    private BigDecimal calPromotionDeduction(String cellId,String supplierBrandId,BigDecimal total,String orderId){
        String canUseDiscount="1";
        String consumerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // get promotion info
        MallFetchPromotionInfoDTO mallFetchPromotionInfoDTO= new MallFetchPromotionInfoDTO();
        mallFetchPromotionInfoDTO.setBrandId(supplierBrandId);
        MallFetchPromotionInfoRO promotionInfo = mallBrandPromotionService.findPromotionInfo(mallFetchPromotionInfoDTO);
        // get promotion benefit
        MallFetchPromotionBenefitRO promotionBenefit = mallBrandPromotionService.findPromotionBenefit(cellId,supplierBrandId);

        if(ObjectUtil.isNull(promotionInfo)){
            return BigDecimal.ZERO;
        }
        // get credit point
        String alreadyGetCreditPoint="1";
        if( (!alreadyGetCreditPoint.equals(promotionBenefit.getAlreadyGetCreditPoint()))
           &&  BrandPromotionTagEnum.OPEN.getMark().equals(promotionInfo.getCreditPointTag())){
            CreditCoupon creditCoupon=new CreditCoupon();
            creditCoupon.setId(IdUtil.simpleUUID())
                    .setCreditPoint(promotionBenefit.getCreditPoint())
                    .setSupplierBrandId(supplierBrandId)
                    .setConsumerBrandId(consumerBrandId)
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            mallCreditCouponMapper.insert(creditCoupon);
            // credit point cnt +1
            mallBrandPromotionMapper.incrementCreditPointCnt(supplierBrandId);
        }

        BigDecimal promotionCreditPointDeductionDifference=BigDecimal.ZERO;

        if(promotionBenefit.getCreditPoint().compareTo(total)>=0){
            promotionCreditPointDeductionDifference=total;
        }

        if(promotionBenefit.getCreditPoint().compareTo(total)<0){
            promotionCreditPointDeductionDifference = promotionBenefit.getCreditPoint();
        }
        BigDecimal earlyBirdDiscountDifference=BigDecimal.ZERO;

        if(canUseDiscount.equals(promotionBenefit.getCanUseEarlyBirdCoupon()) &&
             BrandPromotionTagEnum.OPEN.getMark().equals(promotionInfo.getEarlyBirdDiscountTag()) ){

            BigDecimal c= BigDecimal.valueOf (100 - Integer.parseInt(promotionInfo.getEarlyBirdDiscount()));
            earlyBirdDiscountDifference= total.multiply(c).divide(BigDecimal.valueOf(100),20, RoundingMode.HALF_UP);
            // cnt +1
            mallBrandPromotionMapper.incrementEarlyBirdDiscountCnt(supplierBrandId);
        }
        BigDecimal repurchaseDiscountDifference=BigDecimal.ZERO;
        if(canUseDiscount.equals(promotionBenefit.getCanUseRepurchaseCoupon()) &&
           BrandPromotionTagEnum.OPEN.getMark().equals(promotionInfo.getRepurchaseDiscountTag())){
            BigDecimal d= BigDecimal.valueOf(100-Integer.parseInt(promotionInfo.getRepurchaseDiscount()));
            repurchaseDiscountDifference=total.multiply(d).divide(BigDecimal.valueOf(100),20, RoundingMode.HALF_UP);
           // cnt +1
            mallBrandPromotionMapper.incrementRepurchaseDiscountCnt(supplierBrandId);
        }

        BigDecimal totalPromotionDeduction=promotionCreditPointDeductionDifference
                .add(earlyBirdDiscountDifference).add(repurchaseDiscountDifference);

        BigDecimal promotionDeduction= BigDecimal.ZERO;

         if(total.compareTo(totalPromotionDeduction)>=0){
             promotionDeduction=totalPromotionDeduction;
         }
         if(total.compareTo(totalPromotionDeduction)<0){
             promotionDeduction=total;
         }

         // only promotionDeduction more than 0 , can insert new record
        if(totalPromotionDeduction.compareTo(BigDecimal.ZERO)>0){
            OrderCoupon orderCoupon=new OrderCoupon();
            orderCoupon.setId(IdUtil.simpleUUID())
                    .setOrderId(orderId)
                    .setCellId(cellId)
                    .setConsumerBrandId(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                    .setOrderType(AffiliateOrderTypeEnum.PLAN.getMark())
                    .setEarlyBirdDiscount(Integer.parseInt(promotionInfo.getEarlyBirdDiscount()))
                    .setRepurchaseDiscount(Integer.parseInt(promotionInfo.getEarlyBirdDiscount()))
                    .setCreditPoint(promotionCreditPointDeductionDifference)
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            mallOrderCouponMapper.insert(orderCoupon);
        }

        // update credit coupon point
        if(promotionCreditPointDeductionDifference.compareTo(BigDecimal.ZERO)>0){
            BigDecimal balancePoint=promotionBenefit.getCreditPoint().subtract(promotionCreditPointDeductionDifference);
            mallCreditCouponMapper.updatePointBySupplierAndConsumer(balancePoint,supplierBrandId,consumerBrandId);
        }

        return promotionDeduction;
    }

}
