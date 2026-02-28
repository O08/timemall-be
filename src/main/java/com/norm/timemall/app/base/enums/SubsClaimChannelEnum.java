package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum SubsClaimChannelEnum {
    AUTO("auto","auto claim"),
    EVENT("event","claim by web page"),
    KOL("kol","claim by kol or seller share");
    private String mark;
    private String desc;

    SubsClaimChannelEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (SubsClaimChannelEnum s : SubsClaimChannelEnum.values()) {
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
