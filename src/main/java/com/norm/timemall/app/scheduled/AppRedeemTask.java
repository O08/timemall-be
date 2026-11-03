package com.norm.timemall.app.scheduled;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.norm.timemall.app.base.mo.OrderFlow;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.scheduled.service.TaskAppRedeemScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class AppRedeemTask {
    @Autowired
    private TaskAppRedeemScheduleService taskAppRedeemScheduleService;

    @Autowired
    private OrderFlowService orderFlowService;
    private final String STAGE="schedule";

    /**
     * 每月1号执行
     */
    @Scheduled(cron = "0 0 0 1 * *",zone = "Asia/Shanghai")
    public  void restMonthBuyerOrders(){
        String taskName="RestMonthBuyerOrders";
        OrderFlow autoFlow = orderFlowService.findAutoFlow(taskName, STAGE);
        boolean timeout= !ObjectUtil.isNull(autoFlow) && DateUtil.compare(new Date(), DateUtil.offsetMinute(autoFlow.getCreateAt(), 120)) > 0;
        if(timeout){
            orderFlowService.deleteAutoFlow(taskName, STAGE);
            return;
        }
        if(ObjectUtil.isNotNull(autoFlow)){
            return;
        }


        log.info("app redeem restMonthBuyerOrders  task start.....");
        try {
            orderFlowService.insertAutoFlow(taskName,STAGE);
            taskAppRedeemScheduleService.doRestMonthBuyerOrders();
        }finally {
            orderFlowService.deleteAutoFlow(taskName,STAGE);
        }
        // batch controller
        log.info("app redeem  restMonthBuyerOrders  task end.....");

    }

    /**
     * 每天凌晨4点执行
     */
    @Scheduled(cron = "0 0 4 * * ?",zone = "Asia/Shanghai")
    public  void refreshAppRedeemOrderDashboard(){
        String taskName="RefreshAppRedeemOrderDashboard";
        OrderFlow autoFlow = orderFlowService.findAutoFlow(taskName, STAGE);
        boolean timeout= !ObjectUtil.isNull(autoFlow) && DateUtil.compare(new Date(), DateUtil.offsetMinute(autoFlow.getCreateAt(), 120)) > 0;
        if(timeout){
            orderFlowService.deleteAutoFlow(taskName, STAGE);
            return;
        }
        if(ObjectUtil.isNotNull(autoFlow)){
            return;
        }


        log.info("app redeem RefreshAppRedeemOrderDashboard  task start.....");
        try {
            orderFlowService.insertAutoFlow(taskName,STAGE);
            taskAppRedeemScheduleService.doRefreshAppRedeemOrderDashboard();
        }finally {
            orderFlowService.deleteAutoFlow(taskName,STAGE);
        }
        // batch controller
        log.info("app redeem  RefreshAppRedeemOrderDashboard  task end.....");
    }


}
