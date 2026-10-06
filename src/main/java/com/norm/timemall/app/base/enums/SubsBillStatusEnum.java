package com.norm.timemall.app.base.enums;

public enum SubsBillStatusEnum {
    FREEZE("freeze","冻结中"),
    OPEN("open","待付款"),
    VOID("void","已失效"),
    PAID("paid","已支付"),
    REFUNDED("refunded","已退款")
    ;
    private String mark;
    private String desc;

    SubsBillStatusEnum(String mark, String desc) {
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
