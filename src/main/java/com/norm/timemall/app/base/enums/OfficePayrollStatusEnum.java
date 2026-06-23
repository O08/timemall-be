package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum OfficePayrollStatusEnum {
    PENDING("1","待支付"),
    PAID("2","支付成功")
    ;
    private String mark;
    private String desc;

    OfficePayrollStatusEnum(String mark, String desc) {
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
        for (OfficePayrollStatusEnum s : OfficePayrollStatusEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
