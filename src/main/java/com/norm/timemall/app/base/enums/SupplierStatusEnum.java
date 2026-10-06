package com.norm.timemall.app.base.enums;

import java.util.Objects;

/**
 * 供应商状态枚举
 * ACTIVE-正常、BLACKLISTED-黑名单、TERMINATED-清退、FROZEN-冻结
 */
public enum SupplierStatusEnum {
    ACTIVE("ACTIVE","正常"),
    BLACKLISTED("BLACKLISTED","黑名单"),
    TERMINATED("TERMINATED","清退"),
    FROZEN("FROZEN","冻结")
    ;
    private String mark;
    private String desc;

    SupplierStatusEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (SupplierStatusEnum s : SupplierStatusEnum.values()) {
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