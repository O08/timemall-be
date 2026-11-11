package com.norm.timemall.app.studio.handler;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.mall.mapper.CommonOrderPaymentMapper;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.studio.domain.dto.StudioNewSubscriptionDTO;
import com.norm.timemall.app.studio.mapper.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class StudioSubscribePlanHandler {
    @Autowired
    private StudioSubscriptionMapper studioSubscriptionMapper;
    @Autowired
    private StudioSubsPlanMapper studioSubsPlanMapper;

    @Autowired
    private StudioSubsProductMapper studioSubsProductMapper;

    @Autowired
    private StudioSubsOfferMapper studioSubsOfferMapper;

    @Autowired
    private DefaultPayService defaultPayService;

    @Autowired
    private StudioSubsBillMapper studioSubsBillMapper;







    public void doSubscribePlan(StudioNewSubscriptionDTO dto){
        String buyerBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // validate plan already subscribed
        LambdaQueryWrapper<Subscription> subscriptionLambdaQueryWrapper= Wrappers.lambdaQuery();
        subscriptionLambdaQueryWrapper.eq(Subscription::getPlanId,dto.getPlanId());
        subscriptionLambdaQueryWrapper.eq(Subscription::getSubscriberFid,buyerBrandId);
        subscriptionLambdaQueryWrapper.in(Subscription::getStatus,
                SubscriptionStatusEnum.INCOMPLETE.getMark(),SubscriptionStatusEnum.UNPAID.getMark(),
                SubscriptionStatusEnum.TRIALING.getMark(), SubscriptionStatusEnum.ACTIVE.getMark());

        boolean existsValidSubscriptionRecord = studioSubscriptionMapper.exists(subscriptionLambdaQueryWrapper);
        if(existsValidSubscriptionRecord){
            throw new QuickMessageException("套餐还有正在生效中的订阅记录,订阅失败");
        }
        // validate plan
        SubsPlan plan = studioSubsPlanMapper.selectById(dto.getPlanId());
        if(plan==null){
            throw new QuickMessageException("未找到相关套餐,订阅失败");
        }
        if(!SubsPlanStatusEnum.ONLINE.getMark().equals(plan.getStatus())){
            throw new QuickMessageException("当前套餐未上架销售,订阅失败");
        }
        // validate product and  seller can't buy self item
        SubsProduct product = studioSubsProductMapper.selectById(plan.getProductId());
        if(buyerBrandId.equals(product.getSellerBrandId())){
            throw new QuickMessageException("不能订阅自己的商品,订阅失败");
        }

        // cal bill amount
        BigDecimal billSpan= BigDecimal.ONE;
        if(SubsBillCalendarEnum.QUARTERLY.getMark().equals(dto.getBillCalendar())){
            billSpan=BigDecimal.valueOf(3);
        }
        if(SubsBillCalendarEnum.YEARLY.getMark().equals(dto.getBillCalendar())){
            billSpan=BigDecimal.valueOf(12);
        }

        BigDecimal amount=plan.getPrice().multiply(billSpan);
        // cal coupon money
        BigDecimal couponMoney = calCouponMoney(amount, plan, product, dto);

        // cal netIncome
        BigDecimal netIncome = amount.subtract(couponMoney).compareTo(BigDecimal.valueOf(0.01))>0 ? amount.subtract(couponMoney) : BigDecimal.valueOf(0.01);
        // validate fin info
        FinAccount balanceInfo = defaultPayService.findBalanceInfo(FidTypeEnum.BRAND.getMark(), buyerBrandId);
        if(netIncome.compareTo(balanceInfo.getDrawable())>0){
            throw new QuickMessageException("余额不足，订阅失败");
        }

        // user not first buyer,should not have trial period
        boolean subscriberHaveTrialRecord = haveTrialRecord(plan.getId(), buyerBrandId);
        boolean canEnjoyTrialPeriod=!subscriberHaveTrialRecord && (ObjectUtil.isNotNull(plan.getTrialPeriod()) && plan.getTrialPeriod()>0);
        // new subscription
        // if plan have trial period
        if(canEnjoyTrialPeriod){
            newSubscriptionForTrail(amount,netIncome,product,plan,buyerBrandId, dto.getBillCalendar());
        }
        // if plan not have trial period
        if(!canEnjoyTrialPeriod){
           newSubscriptionForNoTrail(amount,netIncome,product,plan,buyerBrandId,dto.getBillCalendar());
        }

        // update claims info
        if(CharSequenceUtil.isNotBlank(dto.getPromoCode())){
            SubsOffer promoCodeOffer = findPromoCodeOffer(dto.getPromoCode(), product.getSellerBrandId());
            int incrementClaims= ObjectUtil.isNull(promoCodeOffer.getClaims()) ? 1: promoCodeOffer.getClaims()+1;
            int incrementUsed=ObjectUtil.isNull(promoCodeOffer.getUsed())?1: promoCodeOffer.getUsed()+1;
            promoCodeOffer.setClaims(incrementClaims);
            promoCodeOffer.setUsed(incrementUsed);
            promoCodeOffer.setModifiedAt(new Date());
            studioSubsOfferMapper.updateById(promoCodeOffer);
        }
        // increment plan sales
        Long incrementSales=ObjectUtil.isNull(plan.getSales()) ? 1: plan.getSales()+1;
        plan.setSales(incrementSales)
                .setModifiedAt(new Date());
        studioSubsPlanMapper.updateById(plan);



    }



    private void newSubscriptionForTrail(BigDecimal amount,BigDecimal netIncome, SubsProduct product,SubsPlan plan,String buyerBrandId,String billCalendar){

        Subscription subscription = new Subscription();
        subscription.setId(IdUtil.simpleUUID())
                .setName(product.getProductName())
                .setDescription(plan.getDescription())
                .setSubscriberType(FidTypeEnum.BRAND.getMark())
                .setSellerBrandId(product.getSellerBrandId())
                .setSubscriberFid(buyerBrandId)
                .setPlanId(plan.getId())
                .setTrialPeriodStartAt(new Date())
                .setTrialPeriodEndAt(DateUtil.offsetDay(new Date(),plan.getTrialPeriod()))
                .setRecentPlanPrice(plan.getPrice())
                .setStatus(SubscriptionStatusEnum.TRIALING.getMark())
                .setBillCalendar(billCalendar)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioSubscriptionMapper.insert(subscription);


        // new bill
        String transNo= getTransNo();
        SubsBill bill =new SubsBill();
        bill.setId(IdUtil.simpleUUID())
                .setProductName(product.getProductName())
                .setTransNo(transNo)
                .setSubscriptionId(subscription.getId())
                .setPlanId(plan.getId())
                .setSellerBrandId(product.getSellerBrandId())
                .setAmount(amount)
                .setCouponMoney(amount.subtract(netIncome))
                .setNetIncome(netIncome)
                .setBuyerFidType(FidTypeEnum.BRAND.getMark())
                .setBuyerFid(buyerBrandId)
                .setUnfreezeAt(DateUtil.offsetDay(new Date(),plan.getTrialPeriod()))
                .setStatus(SubsBillStatusEnum.FREEZE.getMark())
                .setBillCalendar(billCalendar)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioSubsBillMapper.insert(bill);
    }

    private void newSubscriptionForNoTrail(BigDecimal amount,BigDecimal netIncome,SubsProduct product,SubsPlan plan,String buyerBrandId,String billCalendar){
        int billSpan=1;
        if(SubsBillCalendarEnum.QUARTERLY.getMark().equals(billCalendar)){
            billSpan=3;
        }
        if(SubsBillCalendarEnum.YEARLY.getMark().equals(billCalendar)){
            billSpan=12;
        }

        Subscription subscription = new Subscription();
        subscription.setId(IdUtil.simpleUUID())
                .setName(product.getProductName())
                .setDescription(plan.getDescription())
                .setSubscriberType(FidTypeEnum.BRAND.getMark())
                .setSellerBrandId(product.getSellerBrandId())
                .setSubscriberFid(buyerBrandId)
                .setPlanId(plan.getId())
                .setStartsAt(new Date())
                .setEndsAt(DateUtil.offsetMonth(new Date(),billSpan))
                .setRecentPlanPrice(plan.getPrice())
                .setStatus(SubscriptionStatusEnum.INCOMPLETE.getMark())
                .setBillCalendar(billCalendar)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioSubscriptionMapper.insert(subscription);

        // new bill
        String transNo= getTransNo();
        SubsBill bill =new SubsBill();
        bill.setId(IdUtil.simpleUUID())
                .setProductName(product.getProductName())
                .setTransNo(transNo)
                .setSubscriptionId(subscription.getId())
                .setPlanId(plan.getId())
                .setSellerBrandId(product.getSellerBrandId())
                .setAmount(amount)
                .setCouponMoney(amount.subtract(netIncome))
                .setNetIncome(netIncome)
                .setBuyerFidType(FidTypeEnum.BRAND.getMark())
                .setBuyerFid(buyerBrandId)
                .setBuyerPayAt(new Date())
                .setStatus(SubsBillStatusEnum.OPEN.getMark())
                .setBillCalendar(billCalendar)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        studioSubsBillMapper.insert(bill);

        // pay
        TransferBO transferBO = defaultPayService.generateTransferBO(TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark(), FidTypeEnum.OPERATOR.getMark(), OperatorConfig.sysMidFinAccount,
                FidTypeEnum.BRAND.getMark(), buyerBrandId, netIncome, transNo);
        try {
            String tradeNo = defaultPayService.transfer(new Gson().toJson(transferBO));
            studioSubscriptionMapper.updateStatusAndModifyAtById(subscription.getId(),SubscriptionStatusEnum.ACTIVE.getMark(),new Date());
            studioSubsBillMapper.updateStatusAndModifyAtOnSuccessById(tradeNo,bill.getId(),WhereStoreMoneyEnum.MID.getMark(), SubsBillStatusEnum.PAID.getMark(),new Date());
        }catch (Exception e){
            studioSubscriptionMapper.updateStatusAndModifyAtById(subscription.getId(),SubscriptionStatusEnum.CLOSED.getMark(),new Date());
            studioSubsBillMapper.updateStatusAndModifyAtById(bill.getId(),SubsBillStatusEnum.VOID.getMark(),new Date());
            throw new QuickMessageException(e.getMessage());
        }

    }

    private BigDecimal calCouponMoney(BigDecimal amount,SubsPlan plan,SubsProduct product,StudioNewSubscriptionDTO dto){
        // cal  quarterly \ yearly pay discount
        BigDecimal billCalendarCouponMoney = calPayBillCalendarPromoCouponMoney(amount, product.getId(), dto.getBillCalendar());

        // get promo code offer
        BigDecimal promoCodeCouponMoney = calPromoCodeCouponMoney(product.getId(),amount, plan.getPrice(), dto.getPromoCode(), product.getSellerBrandId());

        return billCalendarCouponMoney.add(promoCodeCouponMoney);

    }
    private BigDecimal calPromoCodeCouponMoney(String targetProductId ,BigDecimal amount ,BigDecimal planPrice, String promoCode,String sellerBrandId){
        BigDecimal giftMoney=BigDecimal.ZERO;
        if(CharSequenceUtil.isBlank(promoCode)){
            return giftMoney;
        }
        SubsOffer promoCodeOffer = findPromoCodeOffer(promoCode, sellerBrandId);
        if(promoCodeOffer==null){
            throw new QuickMessageException("优惠码不可用,操作失败");
        }
        if(!SubsOfferStatusEnum.ONLINE.getMark().equals(promoCodeOffer.getStatus())){
            throw new QuickMessageException("优惠码不可用,操作失败");
        }
        if(ObjectUtil.isNotNull(promoCodeOffer.getClaims()) && promoCodeOffer.getClaims()>=promoCodeOffer.getCapacity()){
            throw new QuickMessageException("优惠码已领完了,操作失败");
        }
        // validate product for: FIRST_PERIOD_CASH_PROMO_CODE_SP and FIRST_PERIOD_DISCOUNT_PROMO_CODE_SP
        boolean needValidateProduct=SubsOfferTypeEnum.FIRST_PERIOD_CASH_PROMO_CODE_SP.getMark().equals(promoCodeOffer.getOfferType())
                || SubsOfferTypeEnum.FIRST_PERIOD_DISCOUNT_PROMO_CODE_SP.getMark().equals(promoCodeOffer.getOfferType());

        if(needValidateProduct && !targetProductId.equals(promoCodeOffer.getForProductId())){
            throw new QuickMessageException("优惠码与商品不匹配,操作失败");
        }

        if(SubsOfferTypeEnum.FULL_ITEM_DISCOUNT_PROMO_CODE.getMark().equals(promoCodeOffer.getOfferType())){
            giftMoney=amount.multiply(BigDecimal.valueOf(promoCodeOffer.getDiscountPercentage())).divide(BigDecimal.valueOf(100),RoundingMode.HALF_UP);
        }

        if(SubsOfferTypeEnum.FIRST_PERIOD_CASH_PROMO_CODE_SP.getMark().equals(promoCodeOffer.getOfferType())){
           giftMoney=promoCodeOffer.getDiscountAmount();
        }
        if(SubsOfferTypeEnum.FIRST_PERIOD_DISCOUNT_PROMO_CODE_SP.getMark().equals(promoCodeOffer.getOfferType())){
           giftMoney=planPrice.multiply(BigDecimal.valueOf(promoCodeOffer.getDiscountPercentage())).divide(BigDecimal.valueOf(100),RoundingMode.HALF_UP);
        }
        return giftMoney;
    }






    /**
     * PAY_YEARLY_DISCOUNT_COUPON_SP
     */
    private BigDecimal calPayBillCalendarPromoCouponMoney(BigDecimal amount,String productId,String billCalendar){
        if(SubsBillCalendarEnum.MONTHLY.getMark().equals(billCalendar)){
            return BigDecimal.ZERO;
        }
        String targetOfferType="";
        if(SubsBillCalendarEnum.QUARTERLY.getMark().equals(billCalendar)){
            targetOfferType=SubsOfferTypeEnum.PAY_QUARTERLY_DISCOUNT_COUPON_SP.getMark();
        }
        if(SubsBillCalendarEnum.YEARLY.getMark().equals(billCalendar)){
            targetOfferType=SubsOfferTypeEnum.PAY_YEARLY_DISCOUNT_COUPON_SP.getMark();
        }
       SubsOffer planOffer = findProductBillCalendarOffer(productId,targetOfferType);
        if(planOffer==null){
            return BigDecimal.ZERO;
        }

        return amount.multiply(BigDecimal.valueOf(planOffer.getDiscountPercentage())).divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP);

    }





    private SubsOffer findProductBillCalendarOffer(String productId,String offerType){
        LambdaQueryWrapper<SubsOffer> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(SubsOffer::getForProductId,productId);
        wrapper.eq(SubsOffer::getOfferType,offerType);
        wrapper.eq(SubsOffer::getStatus, SubsOfferStatusEnum.ONLINE.getMark());
        wrapper.eq(SubsOffer::getClaimChannel, SubsClaimChannelEnum.AUTO.getMark());
        return studioSubsOfferMapper.selectOne(wrapper);
    }
    private SubsOffer findPromoCodeOffer(String promoCode,String sellerBrandId){
        LambdaQueryWrapper<SubsOffer> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(SubsOffer::getPromoCode,promoCode);
        wrapper.eq(SubsOffer::getSellerBrandId,sellerBrandId);
       return studioSubsOfferMapper.selectOne(wrapper);
    }



    private boolean haveTrialRecord(String planId,String buyerBrandId){
        LambdaQueryWrapper<Subscription>wrapper=Wrappers.lambdaQuery();
        wrapper.eq(Subscription::getSubscriberFid,buyerBrandId)
                .eq(Subscription::getSubscriberType,FidTypeEnum.BRAND.getMark())
                .eq(Subscription::getPlanId,planId);
        wrapper.isNotNull(Subscription::getTrialPeriodStartAt);
        return studioSubscriptionMapper.exists(wrapper);
    }

    private String getTransNo(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
        String dateStr = sdf.format(new Date());
        String userStr = SecurityUserHelper.getCurrentPrincipal().getBrandId().substring(0,4).toUpperCase();
        String randomStr = RandomUtil.randomNumbers(4);
        return userStr + dateStr + randomStr;
    }



}
