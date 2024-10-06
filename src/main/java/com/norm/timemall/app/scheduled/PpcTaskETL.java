package com.norm.timemall.app.scheduled;

import cn.hutool.core.date.DateUtil;
import com.norm.timemall.app.scheduled.service.TaskPpcBillScheduleService;
import com.norm.timemall.app.scheduled.service.TaskPpcLinkScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PpcTaskETL {
    @Autowired
    private TaskPpcBillScheduleService taskPpcBillScheduleService;
    @Autowired
    private TaskPpcLinkScheduleService taskPpcLinkScheduleService;


    @Scheduled( cron = "0 0 5 ? * MON",zone = "Asia/Shanghai")
    public void autoPayPpcBill(){

        taskPpcBillScheduleService.autoPayPpcBill();

    }
    @Scheduled( cron = "0 0 1 ? * MON",zone = "Asia/Shanghai")
    public void generatePpcBill(){

        String batch= DateUtil.lastWeek().toDateStr();
        taskPpcBillScheduleService.generatePpcBill(batch);

    }
    @Scheduled( cron = "0 0 1 * * ?",zone = "Asia/Shanghai")
    public void  refreshPpcLinkInfo(){

        taskPpcLinkScheduleService.refreshPpcLinkInfo();

    }

}
