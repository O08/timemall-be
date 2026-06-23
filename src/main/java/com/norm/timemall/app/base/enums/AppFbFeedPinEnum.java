package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppFbFeedPinEnum {
    OFF("0","取消置顶"),
    ON("1","置顶");
    private String mark;
    private String desc;

    AppFbFeedPinEnum(String mark, String desc) {
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
        for (AppFbFeedPinEnum s : AppFbFeedPinEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
