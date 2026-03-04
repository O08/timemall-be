package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppRedeemOrderStatusEnum {
    CREATED, // 已创建订单
    PAID, // 已支付
    DELIVERING, // 交付中
    COMPLETED,// 订单履约完成
    CANCELLED, //取消订单
    REFUNDED, // 已退款
    FAIL,// 失败
    INVALID ;// 失效
    public static boolean validation(String value) {
        for (AppRedeemOrderStatusEnum s : AppRedeemOrderStatusEnum.values()) {
            if (Objects.equals(""+s.ordinal(), value)) {
                return true;
            }
        }
        return false;
    }
}
