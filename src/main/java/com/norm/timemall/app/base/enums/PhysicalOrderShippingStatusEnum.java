package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum PhysicalOrderShippingStatusEnum {
    PENDING("pending", "待发货"),
    SHIPPED("shipped", "已发货"),
    DELIVERED("delivered", "已送达")
    ;
    private String mark;
    private String desc;


    PhysicalOrderShippingStatusEnum(String mark, String desc) {
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
        for (PhysicalOrderShippingStatusEnum s : PhysicalOrderShippingStatusEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
