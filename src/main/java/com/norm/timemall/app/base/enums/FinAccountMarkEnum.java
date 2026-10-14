package com.norm.timemall.app.base.enums;

public enum FinAccountMarkEnum {
    NORMAL("0","正常"),
    WITHDRAW_LIMIT("1","限额处理")
    ;
    private String mark;
    private String desc;

    FinAccountMarkEnum(String mark, String desc) {
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
