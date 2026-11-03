package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppViberCommentInteractEventEnum {
    LIKE("1","点赞"),
    DISLIKE("2","踩");
    
    private String mark;
    private String desc;

    AppViberCommentInteractEventEnum(String mark, String desc) {
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
        for (AppViberCommentInteractEventEnum s : AppViberCommentInteractEventEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
