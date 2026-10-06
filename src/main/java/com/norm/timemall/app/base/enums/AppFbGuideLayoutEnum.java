package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppFbGuideLayoutEnum {
    GALLERY("0","图库"),
    LIST("1","列表");
    private String mark;
    private String desc;

    AppFbGuideLayoutEnum(String mark, String desc) {
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
        for (AppFbGuideLayoutEnum s : AppFbGuideLayoutEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
