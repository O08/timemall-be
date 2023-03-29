package com.norm.timemall.app.base.enums;

public enum ObjectRecordOdEnum {
    SPONSOR("1","发起人"),
    TARGET("2","目标交易方")
    ;
    private String mark;
    private String desc;

    ObjectRecordOdEnum(String mark, String desc) {
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
