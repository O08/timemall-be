package com.norm.timemall.app.base.enums;

public enum SubsOfferClaimStatusEnum {
    CAN_USE("1","待使用"),
    USED("2","已使用"),
    EXPIRED("3","已失效")
    ;
    private String mark;
    private String desc;

    SubsOfferClaimStatusEnum(String mark, String desc) {
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
