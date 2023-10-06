package com.norm.timemall.app.base.enums;

public enum BrandMarkEnum {
    CREATED("1","创建"),
    CLOSED("2","废弃");
    private String mark;
    private String desc;

    BrandMarkEnum(String mark, String desc) {
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
