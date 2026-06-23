package com.norm.timemall.app.base.enums;

public enum DeliverTagEnum {
    CREATED("1","创建"),
    REVISION("2","修订"),
    DELIVERED("3","已交付")
    ;
    private String mark;
    private String desc;

    DeliverTagEnum(String mark, String desc) {
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
