package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum SubsPlanTypeEnum {
    STANDARD("standard","standard plan"),
    FLEX("flex","flexible plan ");
    private String mark;
    private String desc;

    SubsPlanTypeEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (SubsPlanTypeEnum s : SubsPlanTypeEnum.values()) {
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
