package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum OfficePayrollCategoryEnum {
    PERK("1","福利发放"),
    SALARY("2","薪资发放"),
    SALARY_SP("3","单个员工薪资发放")

    ;
    private String mark;
    private String desc;

    OfficePayrollCategoryEnum(String mark, String desc) {
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
        for (OfficePayrollCategoryEnum s : OfficePayrollCategoryEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
