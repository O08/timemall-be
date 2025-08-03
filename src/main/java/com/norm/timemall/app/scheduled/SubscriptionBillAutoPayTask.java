package com.norm.timemall.app.scheduled;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.scheduled.mapper.TaskSubsBillMapper;
import com.norm.timemall.app.scheduled.mapper.TaskSubsPlanMapper;
import com.norm.timemall.app.scheduled.mapper.TaskSubscriptionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SubscriptionBillAutoPayTask {
    @Autowired
    private DefaultPayService defaultPayService;
    @Autowired
    private TaskSubscriptionMapper taskSubscriptionMapper;
    @Autowired
    private TaskSubsBillMapper taskSubsBillMapper;
    @Autowired
    private TaskSubsPlanMapper taskSubsPlanMapper;
    @Autowired
    private OrderFlowService orderFlowService;

    @Scheduled(cron = "0 10/30 * * * ?",zone = "Asia/Shanghai")
    public  void scheduleAutoPayBill(){

        String taskName="SubscriptionBillAutoPayTask";
        OrderFlow autoFlow = orderFlowService.findAutoFlow(taskName, TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark());
        boolean timeout= !ObjectUtil.isNull(autoFlow) && DateUtil.compare(new Date(), DateUtil.offsetMinute(autoFlow.getCreateAt(), 120)) > 0;
        if(timeout){
            orderFlowService.deleteAutoFlow(taskName, TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark());
            return;
        }
        if(ObjectUtil.isNotNull(autoFlow)){
            return;
        }

        log.info("SubscriptionBillAutoPayTask etl task start.....");
        try {
            orderFlowService.insertAutoFlow(taskName,
                    TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark());
            doPaySubscriptionBill();
        }finally {
            orderFlowService.deleteAutoFlow(taskName,
                    TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark());
        }
        // batch controller
        log.info("SubscriptionBillAutoPayTask etl task end.....");
    }

    private void doPaySubscriptionBill(){
        LambdaQueryWrapper<Subscription> subscriptionLambdaQueryWrapper=Wrappers.lambdaQuery();
        subscriptionLambdaQueryWrapper.in(Subscription::getStatus,
                SubscriptionStatusEnum.ACTIVE.getMark(),SubscriptionStatusEnum.UNPAID.getMark());
        subscriptionLambdaQueryWrapper.lt(Subscription::getEndsAt,new Date());
        subscriptionLambdaQueryWrapper.last("limit 500");

        List<Subscription> subscriptions = taskSubscriptionMapper.selectList(subscriptionLambdaQueryWrapper);

        subscriptions.forEach(this::payOneSubscriptionBill);

    }
    private void payOneSubscriptionBill(Subscription subscription){
        // validate plan
        SubsPlan plan = taskSubsPlanMapper.selectById(subscription.getPlanId());
        if(plan==null){
            subscription.setStatus(SubscriptionStatusEnum.CLOSED.getMark());
            modifySubscriptionInfoOnFail(subscription, "未找到相关套餐，续期失败");
            return;
        }
        // plan grace period validate
        int gracePeriod=ObjectUtil.isNull(plan.getGracePeriod()) ? 3 : plan.getGracePeriod();
        boolean exceedGracePeriod=DateUtil.compare(new Date(),DateUtil.offsetDay(subscription.getEndsAt(),gracePeriod))>0;

        if(exceedGracePeriod){
            subscription.setStatus(SubscriptionStatusEnum.INCOMPLETE_EXPIRED.getMark());
            modifySubscriptionInfoOnFail(subscription, "超出宽限期，续期失败");
            return;
        }


        if(!SubsPlanStatusEnum.ONLINE.getMark().equals(plan.getStatus())){
            subscription.setStatus(SubscriptionStatusEnum.UNPAID.getMark());
            modifySubscriptionInfoOnFail(subscription, "套餐未处于售卖状态，续期失败");
            return;
        }


       // check plan price bigger than 6
        BigDecimal deltaPrice=plan.getPrice().subtract(subscription.getRecentPlanPrice());
        BigDecimal deltaPricePercentage= deltaPrice.multiply(BigDecimal.valueOf(100)).divide(subscription.getRecentPlanPrice(), RoundingMode.HALF_UP);

        if(deltaPricePercentage.compareTo(BigDecimal.valueOf(6))>0){
            subscription.setStatus(SubscriptionStatusEnum.CLOSED.getMark());
            modifySubscriptionInfoOnFail(subscription, "套餐涨价超过6%，续期失败");
            return;
        }


        // fin balance validate
        FinAccount balanceInfo = defaultPayService.findBalanceInfo(subscription.getSubscriberType(), subscription.getSubscriberFid());
        if(balanceInfo==null){
            subscription.setStatus(SubscriptionStatusEnum.CLOSED.getMark());
            modifySubscriptionInfoOnFail(subscription, "未匹配到支付账号，续期失败");
            return;
        }

        // cal bill amount
        BigDecimal billSpan= BigDecimal.ONE;
        if(SubsBillCalendarEnum.QUARTERLY.getMark().equals(subscription.getBillCalendar())){
            billSpan=BigDecimal.valueOf(3);
        }
        if(SubsBillCalendarEnum.YEARLY.getMark().equals(subscription.getBillCalendar())){
            billSpan=BigDecimal.valueOf(12);
        }
        BigDecimal amount=plan.getPrice().multiply(billSpan);

        if(amount.compareTo(balanceInfo.getDrawable())>0){
            subscription.setStatus(SubscriptionStatusEnum.UNPAID.getMark());
            modifySubscriptionInfoOnFail(subscription, "余额不足，续期失败");
            return;
        }

        // new bill
        SubsBill bill = newBill(subscription, amount);
        taskSubsBillMapper.insert(bill);

        remotePayService(subscription,bill);


    }

    private void remotePayService(Subscription subscription,SubsBill bill){
        int offsetSpan=1;
        if(SubsBillCalendarEnum.QUARTERLY.getMark().equals(subscription.getBillCalendar())){
            offsetSpan=3;
        }
        if(SubsBillCalendarEnum.YEARLY.getMark().equals(subscription.getBillCalendar())){
            offsetSpan=12;
        }
        try {
            TransferBO transferBO = defaultPayService.generateTransferBO(TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark(), FidTypeEnum.OPERATOR.getMark(), OperatorConfig.sysMidFinAccount,
                    bill.getBuyerFidType(), bill.getBuyerFid(), bill.getNetIncome(), bill.getTransNo());

            String tradeNo = defaultPayService.transfer(new Gson().toJson(transferBO));
            Date endsAt=DateUtil.offsetMonth(subscription.getEndsAt(),offsetSpan);
            taskSubscriptionMapper.updateSubscriptionOnSuccess(subscription.getId(),SubscriptionStatusEnum.ACTIVE.getMark(),new Date(),endsAt);
            taskSubsBillMapper.updateStatusAndModifyAtOnSuccessById(tradeNo,bill.getId(), WhereStoreMoneyEnum.MID.getMark(), SubsBillStatusEnum.PAID.getMark(),new Date());
        }catch (Exception e){
            log.info("auto pay subscription bill  fail,reason:" +e.getMessage());
            subscription.setStatus(SubscriptionStatusEnum.UNPAID.getMark());
            modifySubscriptionInfoOnFail(subscription, "调用支付服务异常，续期失败");
            taskSubsBillMapper.updateStatusAndModifyAtById(bill.getId(),SubsBillStatusEnum.VOID.getMark(),new Date());
        }
    }

    private SubsBill newBill(Subscription subscription,BigDecimal amount){
        String transNo= getTransNo(subscription.getSubscriberFid());
        SubsBill bill =new SubsBill();
        bill.setId(IdUtil.simpleUUID())
                .setProductName(subscription.getName())
                .setTransNo(transNo)
                .setSubscriptionId(subscription.getId())
                .setPlanId(subscription.getPlanId())
                .setSellerBrandId(subscription.getSellerBrandId())
                .setAmount(amount)
                .setNetIncome(amount)
                .setBuyerFidType(subscription.getSubscriberType())
                .setBuyerFid(subscription.getSubscriberFid())
                .setBuyerPayAt(new Date())
                .setStatus(SubsBillStatusEnum.OPEN.getMark())
                .setBillCalendar(subscription.getBillCalendar())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        return bill;
    }
    private void modifySubscriptionInfoOnFail(Subscription subscription, String remark) {
        subscription.setModifiedAt(new Date());
        subscription.setRemark(remark);
        taskSubscriptionMapper.updateById(subscription);

    }
    private String getTransNo(String buyerBrandId){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmm");
        String dateStr = sdf.format(new Date());
        String userStr = buyerBrandId.substring(0,4).toUpperCase();
        String randomStr = RandomUtil.randomNumbers(4);
        return userStr + dateStr + randomStr;
    }


}
