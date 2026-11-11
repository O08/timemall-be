package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum VirtualOrderTagEnum {
    CREATING, // 订单创建中
    CREATED, // 已创建订单
    WAITING_PAY, // 等待支付
    PAID, // 已支付
    DELIVERING, // 交付中
    COMPLETED,// 订单履约完成
    CANCELLED, //取消订单
    REFUNDED, // 已退款
    REMITTANCE , // 已打款关单
    APPLY_REFUND, // 申请退款
    FAIL,// 失败
    INVALID ;// 失效
    public static boolean validation(String value) {
        for (VirtualOrderTagEnum s : VirtualOrderTagEnum.values()) {
            if (Objects.equals(""+s.ordinal(), value)) {
                return true;
            }
        }
        return false;
    }
}
