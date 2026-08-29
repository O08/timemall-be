package com.norm.timemall.app.base.enums;

public enum BillCategoiesEnum {
    CELL("cell","cell"),
    PROPOSAL("proposal","proposal"),
    ;
    private String mark;
    private String desc;

    BillCategoiesEnum(String mark, String desc) {
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
