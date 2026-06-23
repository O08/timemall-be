package com.norm.timemall.app.base.enums;

public enum AffiliateOrderMarketEnum {
    API("api","api market"),
    BLANK("blank","no affiliate order"),
    LINK("link","link market");
    private String mark;
    private String desc;

    AffiliateOrderMarketEnum(String mark, String desc) {
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
}
