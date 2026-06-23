package com.norm.timemall.app.base.enums;

public enum PpcBillTagEnum {
    REMIT("1","财务审核"),
    PAID("2","已打款");
    private String mark;
    private String desc;

    PpcBillTagEnum(String mark, String desc) {
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
