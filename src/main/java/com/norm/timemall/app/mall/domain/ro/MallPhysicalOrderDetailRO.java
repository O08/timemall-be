package com.norm.timemall.app.mall.domain.ro;

import com.norm.timemall.app.base.mo.PhysicalOrder;
import com.norm.timemall.app.base.mo.PhysicalOrderItem;
import lombok.Data;

import java.util.List;

/**
 * 实物订单详情（含商品明细），供邮件通知等下游消费。
 */
@Data
public class MallPhysicalOrderDetailRO {
    private PhysicalOrder order;
    private List<PhysicalOrderItem> items;
}
