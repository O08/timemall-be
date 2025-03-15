package com.norm.timemall.app.base.enums;

import java.util.Objects;

public enum AppCardGuideLayoutEnum {
    ART("art","art"),
    VIDEO("video","video"),
    MUSIC("music","music"),
    FILM("film","film"),
    DESIGN("design","design"),
    BOOK("book","book")
    ;
    private String mark;
    private String desc;

    AppCardGuideLayoutEnum(String mark, String desc) {
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
        for (AppCardGuideLayoutEnum s : AppCardGuideLayoutEnum.values()) {
            if (Objects.equals(s.getMark(), value)) {
                return true;
            }
        }
        return false;
    }
}
