package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum PhysicalOrderPaymentStatusEnum {
    /**
     * Unpaid (未支付) - Initial state when the order is generated but no cash is captured yet.
     */
    UNPAID("UNPAID", "未支付"),

    /**
     * Paid (已支付) - Payment gateway (WeChat/Alipay) successfully captured the full amount.
     */
    PAID("PAID", "已支付"),

    /**
     * Partial Refunded (部分退款) - Triggered when some items are out of stock or returned, but others remain shipped.
     */
    PARTIAL_REFUNDED("PARTIAL_REFUNDED", "部分退款"),

    /**
     * Fully Refunded (全额退款) - The entire order amount has been returned to the buyer.
     */
    FULLY_REFUNDED("FULLY_REFUNDED", "全额退款"),
    REMITTING("REMITTING", "结算中"),
    ALREADY_REMITTANCE("ALREADY_REMITTANCE", "已收取货款"),
    CANCELLED("CANCELLED","已取消"),
    CLOSED("CLOSED","已关闭")

    ;

    private final String mark;
    private final String description;

    PhysicalOrderPaymentStatusEnum(String mark, String description) {
        this.mark = mark;
        this.description = description;
    }

    public String getMark() {
        return mark;
    }

    public String getDescription() {
        return description;
    }
    public static boolean validation(String value) {
        for (PhysicalOrderPaymentStatusEnum s : PhysicalOrderPaymentStatusEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }

}
