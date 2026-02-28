package com.norm.timemall.app.scheduled;

import cn.hutool.core.date.DateUtil;
import com.norm.timemall.app.scheduled.service.TaskPpcBillScheduleService;
import com.norm.timemall.app.scheduled.service.TaskPpcLinkScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PpcTaskETL {
    @Autowired
    private TaskPpcBillScheduleService taskPpcBillScheduleService;
    @Autowired
    private TaskPpcLinkScheduleService taskPpcLinkScheduleService;


    @Scheduled( cron = "0 0 5 ? * MON",zone = "Asia/Shanghai")
    public void autoPayPpcBill(){
        log.info("autoPayPpcBill etl task start.....");
        taskPpcBillScheduleService.autoPayPpcBill();
        log.info("autoPayPpcBill etl task end.....");

    }
    @Scheduled( cron = "0 0 1 ? * MON",zone = "Asia/Shanghai")
    public void generatePpcBill(){

        String batch= DateUtil.lastWeek().toDateStr().replace("-","");
        log.info(batch+" batch taskPpcBillScheduleService etl task start.....");
        taskPpcBillScheduleService.generatePpcBill(batch);
        log.info(batch+" batch taskPpcBillScheduleService etl task end.....");


   }
    @Scheduled( cron = "0 0 1 * * ?",zone = "Asia/Shanghai")
    public void  refreshPpcLinkInfo(){
        log.info("refreshPpcLinkInfo etl task start.....");
        taskPpcLinkScheduleService.refreshPpcLinkInfo();
        log.info("refreshPpcLinkInfo etl task end.....");


    }

}
