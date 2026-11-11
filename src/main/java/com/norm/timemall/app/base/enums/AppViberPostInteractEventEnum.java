package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppViberPostInteractEventEnum {
    LIKE("1","点赞"),
    CANCEL_LIKE("2","取消点赞"),
    SHARE("3","分享");

    private String mark;
    private String desc;

    AppViberPostInteractEventEnum(String mark, String desc) {
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
        for (AppViberPostInteractEventEnum s : AppViberPostInteractEventEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}