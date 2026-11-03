package com.norm.timemall.app.base.enums;

public enum CustomerMarkEnum {
    NORMAL("0","正常"),
    FREEZE("1","已冻结")
    ;
    private String mark;
    private String desc;

    CustomerMarkEnum(String mark, String desc) {
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
