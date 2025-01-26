package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppFbFeedHighlightEnum {
    OFF("0","普通帖子"),
    ON("1","加精帖子");
    private String mark;
    private String desc;

    AppFbFeedHighlightEnum(String mark, String desc) {
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
        for (AppFbFeedHighlightEnum s : AppFbFeedHighlightEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
