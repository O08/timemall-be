package com.norm.timemall.app.base.enums;

public enum ObjectRecordTagEnum {
    CREATED("1","创建"),
    ACCEPT("2","接受"),
    DENY("3","拒绝");
    private String mark;
    private String desc;

    ObjectRecordTagEnum(String mark, String desc) {
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
