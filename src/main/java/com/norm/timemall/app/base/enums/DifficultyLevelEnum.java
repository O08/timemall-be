package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum DifficultyLevelEnum {
    EASY("easy","初级"),
    MEDIUM("medium","中级"),
    ADVANCED("advanced","高级"),
    EXPERT("expert","专家级"),
    MASTER("master","大师级")

    ;
    private String mark;
    private String desc;

    DifficultyLevelEnum(String mark, String desc) {
        this.mark = mark;
        this.desc = desc;
    }

    public static boolean validation(String value) {
        for (DifficultyLevelEnum s : DifficultyLevelEnum.values()) {
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
