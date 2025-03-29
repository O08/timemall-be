package com.norm.timemall.app.base.enums;

public enum BillMarkEnum {
    CREATED("1","创建"),
    PENDING("2","待支付"),
    PAID("3","已支付")
    ;
    private String mark;
    private String desc;

    BillMarkEnum(String mark, String desc) {
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
