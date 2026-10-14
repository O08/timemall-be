package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum VirtualProductShippingMethodEnum {
    STANDARD("standard","标准发货"),
    RANDOM("random","随机发货"),
    MANUAL("manual","手动发货")
    ;
    private String mark;
    private String desc;

    VirtualProductShippingMethodEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (VirtualProductShippingMethodEnum s : VirtualProductShippingMethodEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
