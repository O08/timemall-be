package com.norm.timemall.app.scheduled;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
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

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SubscriptionFreezeBillPayTask {
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

    @Scheduled(cron = "0 15/30 * * * ?",zone = "Asia/Shanghai")
    public  void scheduleAutoPayFreezeBill(){
        String taskName="SubscriptionFreezeBillPayTask";
        OrderFlow autoFlow = orderFlowService.findAutoFlow(taskName, TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark());
        boolean timeout= !ObjectUtil.isNull(autoFlow) && DateUtil.compare(new Date(), DateUtil.offsetMinute(autoFlow.getCreateAt(), 120)) > 0;
        if(timeout){
            orderFlowService.deleteAutoFlow(taskName, TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark());
            return;
        }
        if(ObjectUtil.isNotNull(autoFlow)){
            return;
        }

        log.info("SubscriptionFreezeBillPayTask etl task start.....");
        try {
            orderFlowService.insertAutoFlow(taskName,
                    TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark());
            doPayBill();
        }finally {
            orderFlowService.deleteAutoFlow(taskName,
                    TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark());
        }
        // batch controller
        log.info("SubscriptionFreezeBillPayTask etl task end.....");
    }

    private void doPayBill(){

        LambdaQueryWrapper<SubsBill> billLambdaQueryWrapper= Wrappers.lambdaQuery();
        billLambdaQueryWrapper.eq(SubsBill::getStatus, SubsBillStatusEnum.FREEZE.getMark());
        billLambdaQueryWrapper.lt(SubsBill::getUnfreezeAt,new Date());
        billLambdaQueryWrapper.last("limit 500");

        List<SubsBill> bills = taskSubsBillMapper.selectList(billLambdaQueryWrapper);

        bills.forEach(this::payOneBill);


    }
    private void payOneBill(SubsBill bill){
        // validate subscription
        Subscription subscription =taskSubscriptionMapper.selectById(bill.getSubscriptionId());
        if(subscription==null){
            bill.setStatus(SubsBillStatusEnum.VOID.getMark());
            modifyBillInfoOnFail(bill,"未匹配到订阅记录，订阅失败");

            return;
        }
        if(!SubscriptionStatusEnum.TRIALING.getMark().equals(subscription.getStatus())){
            bill.setStatus(SubsBillStatusEnum.VOID.getMark());
            modifyBillInfoOnFail(bill,"订阅记录校验不通过，订阅失败");

            subscription.setStatus(SubscriptionStatusEnum.CLOSED.getMark());
            subscription.setModifiedAt(new Date());
            taskSubscriptionMapper.updateById(subscription);
            return;
        }
        
        // validate plan
        SubsPlan plan = taskSubsPlanMapper.selectById(bill.getPlanId());
        if(plan==null){
            bill.setStatus(SubsBillStatusEnum.VOID.getMark());
            modifyBillInfoOnFail(bill, "未找到相关套餐，订阅失败");

            subscription.setStatus(SubscriptionStatusEnum.CLOSED.getMark());
            subscription.setModifiedAt(new Date());
            taskSubscriptionMapper.updateById(subscription);
            return;
        }

        
        // plan grace period validate
 
        int gracePeriod=ObjectUtil.isNull(plan.getGracePeriod()) ? 3 : plan.getGracePeriod();
        boolean exceedGracePeriod=DateUtil.compare(new Date(),DateUtil.offsetDay(bill.getUnfreezeAt(),gracePeriod))>0;

        if(exceedGracePeriod){
            bill.setStatus(SubsBillStatusEnum.VOID.getMark());
            modifyBillInfoOnFail(bill,"超出宽限期，订阅失败");

            subscription.setStatus(SubscriptionStatusEnum.INCOMPLETE_EXPIRED.getMark());
            subscription.setModifiedAt(new Date());
            taskSubscriptionMapper.updateById(subscription);
            return;
        }


        if(!SubsPlanStatusEnum.ONLINE.getMark().equals(plan.getStatus())){
            modifyBillInfoOnFail(bill,"套餐未处于售卖状态，订阅失败");
            return;
        }

     

        // plan price validate not need for first period
        // fin balance validate
        FinAccount balanceInfo = defaultPayService.findBalanceInfo(bill.getBuyerFidType(), bill.getBuyerFid());
        if(balanceInfo==null){
            bill.setStatus(SubsBillStatusEnum.VOID.getMark());
            modifyBillInfoOnFail(bill,"未匹配到支付账号，订阅失败");
            return;
        }
        if(bill.getNetIncome().compareTo(balanceInfo.getDrawable())>0){
            modifyBillInfoOnFail(bill,"余额不足，订阅失败");
            return;
        }
        int offsetSpan=1;
        if(SubsBillCalendarEnum.QUARTERLY.getMark().equals(subscription.getBillCalendar())){
            offsetSpan=3;
        }
        if(SubsBillCalendarEnum.YEARLY.getMark().equals(subscription.getBillCalendar())){
            offsetSpan=12;
        }
        Date endsAt=DateUtil.offsetMonth(subscription.getEndsAt(),offsetSpan);

        try {
            TransferBO transferBO = defaultPayService.generateTransferBO(TransTypeEnum.SUBSCRIPTION_BILL_PAY.getMark(), FidTypeEnum.OPERATOR.getMark(), OperatorConfig.sysMidFinAccount,
                    bill.getBuyerFidType(), bill.getBuyerFid(), bill.getNetIncome(), bill.getTransNo());

            String tradeNo = defaultPayService.transfer(new Gson().toJson(transferBO));
            taskSubscriptionMapper.updateSubscriptionOnSuccess(subscription.getId(),SubscriptionStatusEnum.ACTIVE.getMark(),new Date(),endsAt);
            taskSubsBillMapper.updateStatusAndModifyAtOnSuccessById(tradeNo,bill.getId(), WhereStoreMoneyEnum.MID.getMark(), SubsBillStatusEnum.PAID.getMark(),new Date());
        }catch (Exception e){
            log.info("freeze bill pay fail,reason:" +e.getMessage());
            modifyBillInfoOnFail(bill,"调用支付服务异常，订阅失败");
        }

    }
    private void  modifyBillInfoOnFail(SubsBill bill, String remark){

        bill.setModifiedAt(new Date());
        bill.setRemark(remark);
        taskSubsBillMapper.updateById(bill);

    }
}
