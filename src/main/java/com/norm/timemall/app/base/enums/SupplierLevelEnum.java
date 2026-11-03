package com.norm.timemall.app.base.enums;

import java.util.Objects;

/**
 * 供应商类型枚举
 * STRATEGIC-战略型、LEVERAGE-杠杆型、BOTTLENECK-瓶颈型、ROUTINE-常规型
 */
public enum SupplierLevelEnum {
    STRATEGIC("STRATEGIC","战略型"),
    LEVERAGE("LEVERAGE","杠杆型"),
    BOTTLENECK("BOTTLENECK","瓶颈型"),
    ROUTINE("ROUTINE","常规型")
    ;
    private String mark;
    private String desc;

    SupplierLevelEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (SupplierLevelEnum s : SupplierLevelEnum.values()) {
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