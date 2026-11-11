package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum PhysicalOrderStatusEnum {
    PENDING_PAYMENT("pending_payment", "待支付"),
    PAID("paid", "已支付/待发货"),
    SHIPPED("shipped", "已发货"),
    COMPLETED("completed", "已完成"),
    CANCELLED("cancelled", "已取消"),
    CLOSED("closed", "已关闭"),
    REFUNDED("refunded", "已退款")
    ;

    private String mark;
    private String desc;

    PhysicalOrderStatusEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public String getMark() {
        return mark;
    }

    public String getDesc() {
        return desc;
    }

    public static boolean validation(String value) {
        for (PhysicalOrderStatusEnum s : PhysicalOrderStatusEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
