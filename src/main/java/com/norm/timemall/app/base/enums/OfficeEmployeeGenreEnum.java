package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum OfficeEmployeeGenreEnum {
    FULL_TIME("1","全职员工"),
    PART_TIME("2","兼职员工"),
    TEMPORARY_LABOR("3","临时工"),
    INTERN("4","实习生"),
    CONTRACT_LABOR("5","合同工"),
    VOLUNTARY("6","志愿者"),
    DISPATCH_LABOR("7","派遣员工")
    ;
    private String mark;
    private String desc;

    OfficeEmployeeGenreEnum(String mark, String desc) {
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
        for (OfficeEmployeeGenreEnum s : OfficeEmployeeGenreEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
