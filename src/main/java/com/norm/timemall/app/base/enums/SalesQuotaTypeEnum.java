package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum SalesQuotaTypeEnum {
    PER_PERSON("per_person","每人限购"),
    MONTH_LIMIT("month_limit","每月限购");
    private String mark;
    private String desc;

    SalesQuotaTypeEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
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

    public static boolean validation(String value) {
        for (SalesQuotaTypeEnum s : SalesQuotaTypeEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
