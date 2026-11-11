package com.norm.timemall.app.scheduled;

import com.norm.timemall.app.base.mo.PhysicalOrder;
import com.norm.timemall.app.base.service.OrderFlowService;
import com.norm.timemall.app.scheduled.service.TaskPhysicalOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class CancelPhysicalOrderETL {
    @Autowired
    private TaskPhysicalOrderService physicalOrderService;

    @Autowired
    private OrderFlowService orderFlowService;

    private static final int MICRO_BATCH_SIZE = 20;
    private static final long PAUSE_MILLIS = 150;


    /**
     * 每隔 1 分钟执行一次扫表
     * 自动扫描并清理 15 分钟前下单但仍未付款的僵尸订单
     */
    @Scheduled(cron = "0 */1 * * * ?")
    public void scanAndCancelTimeoutOrders() {
        String taskName = "ScanAndCancelTimeoutPhysicalOrdersTask";
        String stage = "cancel";

        // 尝试抢锁
        if (!orderFlowService.tryLock(taskName, stage, 30)) {
            return;
        }
        log.info("ScanAndCancelTimeoutPhysicalOrdersTask etl task start.....");
        try {
            //  执行核心 ETL 业务
            doScanAndCancelTimeoutOrders();
        } catch (Exception e) {
            log.error("ETL任务执行失败", e);
        } finally {
            // 释放锁
            orderFlowService.unlock(taskName, stage);
            log.info("ScanAndCancelTimeoutPhysicalOrdersTask etl task end.....");
        }
        
    }
    private void doScanAndCancelTimeoutOrders(){
        int totalCancelled = 0;

        //  Fetch the oldest 500 target IDs using Index-Covering Search
        List<PhysicalOrder> records = physicalOrderService.getTimeoutUnpaidRecords();
        int totalRecords = records.size();

        // Micro-Batching Loop: Partition the 500 rows into segments of 20
        for (int i = 0; i < totalRecords; i += MICRO_BATCH_SIZE) {
            int end = Math.min(i + MICRO_BATCH_SIZE, totalRecords);
            List<PhysicalOrder> microBatch = records.subList(i, end);

            // Process the current micro-batch of 20 records sequentially
            for (PhysicalOrder order : microBatch) {
                try {
                    physicalOrderService.cancelTimeoutOrder(order.getId());
                    totalCancelled++;
                } catch (Exception e) {
                    log.error("【超时关单】取消单笔订单 [{}] 失败: {}", order.getId(), e.getMessage());
                }
            }

            if (end < totalRecords) {
                try {
                    TimeUnit.MILLISECONDS.sleep(PAUSE_MILLIS);
                } catch (InterruptedException e) {
                    log.error("【超时关单】批次冷却中断: {}", e.getMessage());
                    Thread.currentThread().interrupt(); // Restore interrupted status gracefully
                    break;
                }
            }
        }

        if (totalCancelled > 0) {
            log.info("【超时关单任务】分批调度清理完毕，成功释放订单：{} 笔", totalCancelled);
        }
    }

}