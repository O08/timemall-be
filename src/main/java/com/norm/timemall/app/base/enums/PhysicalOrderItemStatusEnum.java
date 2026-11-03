package com.norm.timemall.app.base.enums;

public enum PhysicalOrderItemStatusEnum {
    PENDING_PAYMENT("pending_payment", "待支付"),
    PAID("paid", "已支付"),
    REFUNDED("refunded", "已退款"),
    CANCELLED("cancelled", "已取消"),
    CLOSED("closed","已关闭"),
    SETTLED("settled", "已结算")
    ;
    private String mark;
    private String desc;

    PhysicalOrderItemStatusEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public String getMark() {
        return mark;
    }

    public String getDesc() {
        return desc;
    }
}
