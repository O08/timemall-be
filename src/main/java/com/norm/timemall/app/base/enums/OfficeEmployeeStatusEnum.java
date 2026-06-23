package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum OfficeEmployeeStatusEnum {
    ON_JOB("1","在职"),
    RESIGNED("2","离职"),
    ON_VOCATION("3","休假"),
    PROBATION_PERIOD("4","试用期"),
    INJURY("5","工伤")
    ;
    private String mark;
    private String desc;

    OfficeEmployeeStatusEnum(String mark, String desc) {
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
        for (OfficeEmployeeStatusEnum s : OfficeEmployeeStatusEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
