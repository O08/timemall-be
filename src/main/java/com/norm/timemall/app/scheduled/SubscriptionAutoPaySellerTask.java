package com.norm.timemall.app.scheduled;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.gson.Gson;
import com.norm.timemall.app.base.config.OperatorConfig;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.mo.OrderFlow;
import com.norm.timemall.app.base.mo.SubsBill;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.scheduled.mapper.TaskSubsBillMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class SubscriptionAutoPaySellerTask {
    @Autowired
    private DefaultPayService defaultPayService;

    @Autowired
    private TaskSubsBillMapper taskSubsBillMapper;

    @Autowired
    private OrderFlowService orderFlowService;
    @Scheduled(cron = "0 5/30 * * * ?",zone = "Asia/Shanghai")
    public  void scheduleAutoPaySeller(){
        String taskName="SubscriptionAutoPaySellerTask";

        OrderFlow autoFlow = orderFlowService.findAutoFlow(taskName, TransTypeEnum.SUBSCRIPTION_BILL_REMITTANCE.getMark());
        boolean timeout= !ObjectUtil.isNull(autoFlow) && DateUtil.compare(new Date(), DateUtil.offsetMinute(autoFlow.getCreateAt(), 120)) > 0;
        if(timeout){
            orderFlowService.deleteAutoFlow(taskName, TransTypeEnum.SUBSCRIPTION_BILL_REMITTANCE.getMark());
            return;
        }
        if(ObjectUtil.isNotNull(autoFlow)){
            return;
        }


        log.info("SubscriptionAutoPaySellerTask etl task start.....");
        try {
            orderFlowService.insertAutoFlow(taskName,
                    TransTypeEnum.SUBSCRIPTION_BILL_REMITTANCE.getMark());
            doPaySubscriptionSellerBill();
        }finally {
            orderFlowService.deleteAutoFlow(taskName,
                    TransTypeEnum.SUBSCRIPTION_BILL_REMITTANCE.getMark());
        }
        // batch controller
        log.info("SubscriptionAutoPaySellerTask etl task end.....");
    }

    private void doPaySubscriptionSellerBill(){
        LambdaQueryWrapper<SubsBill> billLambdaQueryWrapper=Wrappers.lambdaQuery();
        billLambdaQueryWrapper.eq(SubsBill::getStatus, SubsBillStatusEnum.PAID.getMark())
                               .eq(SubsBill::getWhereStoreMoney, WhereStoreMoneyEnum.MID.getMark())
                .lt(SubsBill::getBuyerPayAt, DateUtil.offsetDay(new Date(),-7));

        billLambdaQueryWrapper.last("limit 500");

        List<SubsBill> subsBills = taskSubsBillMapper.selectList(billLambdaQueryWrapper);

        subsBills.forEach(this::remittanceToSeller);

    }

    private void remittanceToSeller(SubsBill bill){
        try {
            TransferBO transferBO = defaultPayService.generateTransferBO(TransTypeEnum.SUBSCRIPTION_BILL_REMITTANCE.getMark(), FidTypeEnum.BRAND.getMark(), bill.getSellerBrandId(),
                    FidTypeEnum.OPERATOR.getMark(), OperatorConfig.sysMidFinAccount, bill.getNetIncome(), bill.getTransNo());

            defaultPayService.transfer(new Gson().toJson(transferBO));
            taskSubsBillMapper.updateStatusAndModifyAtOnSuccessRemittanceById(bill.getId(), WhereStoreMoneyEnum.SELLER.getMark(),new Date());
        }catch (Exception e){
            log.info("subscription auto remittance to seller fail,reason:" +e.getMessage());
        }
     }


}
