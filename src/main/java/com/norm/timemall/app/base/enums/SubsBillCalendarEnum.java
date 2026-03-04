package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum SubsBillCalendarEnum {
    MONTHLY("monthly","pay monthly"),
    QUARTERLY("quarterly","pay quarterly"),
    YEARLY("yearly","pay yearly");
    private String mark;
    private String desc;

    SubsBillCalendarEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (SubsBillCalendarEnum s : SubsBillCalendarEnum.values()) {
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
