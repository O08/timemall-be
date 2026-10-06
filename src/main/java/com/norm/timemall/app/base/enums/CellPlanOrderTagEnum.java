package com.norm.timemall.app.base.enums;

public enum CellPlanOrderTagEnum {
    CREATING, // 订单创建中
    CREATED, // 已创建订单
    WAITING_PAY, // 等待支付
    PAID, // 已支付
    DELIVERING, // 交付中
    COMPLETED,// 订单履约完成
    CANCELLED, //取消订单
    REFUNDED, // 已退款
    FAIL,// 失败
    INVALID // 失效
}
