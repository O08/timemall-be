package com.norm.timemall.app.scheduled.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.PhysicalOrderItemStatusEnum;
import com.norm.timemall.app.base.enums.PhysicalOrderPaymentStatusEnum;
import com.norm.timemall.app.base.enums.PhysicalOrderStatusEnum;
import com.norm.timemall.app.base.mo.PhysicalOrder;
import com.norm.timemall.app.base.mo.PhysicalOrderItem;
import com.norm.timemall.app.scheduled.mapper.TaskPhysicalOrderItemMapper;
import com.norm.timemall.app.scheduled.mapper.TaskPhysicalOrderMapper;
import com.norm.timemall.app.scheduled.mapper.TaskPhysicalProductMapper;
import com.norm.timemall.app.scheduled.service.TaskPhysicalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class TaskPhysicalOrderServiceImpl implements TaskPhysicalOrderService {
    @Autowired
    private TaskPhysicalOrderMapper physicalOrderMapper;
    @Autowired
    private TaskPhysicalOrderItemMapper physicalOrderItemMapper;

    @Autowired
    private TaskPhysicalProductMapper physicalProductMapper;

    @Override
    public List<PhysicalOrder> getTimeoutUnpaidRecords() {
        // 动态计算出15分钟前的绝对时间边界点
        Date timeoutBoundary = DateUtil.offsetMinute(new Date(), -15);

        LambdaQueryWrapper<PhysicalOrder> queryWrapper = Wrappers.lambdaQuery();

        // 只拉取 ID 字段，完美防止海量待支付场景下把 JVM 内存撑爆
        queryWrapper.select(PhysicalOrder::getId);

        // 限制主订单状态必须是 PENDING_PAYMENT（待支付）
        queryWrapper.eq(PhysicalOrder::getOrderStatus, PhysicalOrderStatusEnum.PENDING_PAYMENT.getMark());

        // 限制支付资金状态必须是 UNPAID（未支付），双重防线防止误伤已付钱的订单
        queryWrapper.eq(PhysicalOrder::getPaymentStatus, PhysicalOrderPaymentStatusEnum.UNPAID.getMark());

        // 创建时间必须早于 15 分钟前
        queryWrapper.lt(PhysicalOrder::getCreateAt, timeoutBoundary);

        // 按创建时间升序排列，确保优先挑出堆积最久远的僵尸单进行清理
        queryWrapper.orderByAsc(PhysicalOrder::getCreateAt);

        queryWrapper.last("LIMIT 500");

        // 执行查询并直接返回最轻量级的结果集列表
        return physicalOrderMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelTimeoutOrder(String orderId) {
        //锁住主订单，防止发生并发冲突
        PhysicalOrder order = physicalOrderMapper.selectForUpdate(orderId);
        if (order == null) {
            return;
        }

        // 只有当订单【依旧是未支付】时才允许关闭！
        // 如果在锁等待期间，外部付款回调已经把状态改成 PAID 了，这里锁醒来后查出来的状态就是 PAID，从而安全拦截退出
        if (!PhysicalOrderPaymentStatusEnum.UNPAID.getMark().equals(order.getPaymentStatus())) {
            return;
        }
        if (!PhysicalOrderStatusEnum.PENDING_PAYMENT.getMark().equals(order.getOrderStatus())) {
            return;
        }

        Date now = new Date();

        // 释放被锁死的子订单库存：查出明细，批量原子还回货架
        List<PhysicalOrderItem> items = physicalOrderItemMapper.selectList(
                Wrappers.<PhysicalOrderItem>lambdaQuery().eq(PhysicalOrderItem::getOrderId, orderId)
        );
        for (PhysicalOrderItem item : items) {
            physicalProductMapper.restoreInventory(item.getProductId(), item.getQuantity(), now);
        }

        // 批量更新所有子订单状态
        physicalOrderItemMapper.update(null,
                new LambdaUpdateWrapper<PhysicalOrderItem>()
                        .eq(PhysicalOrderItem::getOrderId, orderId)
                        .set(PhysicalOrderItem::getStatus, PhysicalOrderItemStatusEnum.CLOSED.getMark()) // 明细标记为已取消
                        .set(PhysicalOrderItem::getModifiedAt, now)
        );

        // 更新主订单状态为最终关闭态
        PhysicalOrder updateOrder = new PhysicalOrder()
                .setId(order.getId())
                .setPaymentStatus(PhysicalOrderPaymentStatusEnum.CLOSED.getMark())
                .setOrderStatus(PhysicalOrderStatusEnum.CLOSED.getMark())
                .setModifiedAt(now);
        physicalOrderMapper.updateById(updateOrder);
    }
}
