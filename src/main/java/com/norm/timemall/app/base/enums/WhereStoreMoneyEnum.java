package com.norm.timemall.app.base.enums;

public enum WhereStoreMoneyEnum {
    MID("mid","平台托管"),
    SELLER("seller","商家"),
    BUYER("buyer","买家"),
    ;
    private String mark;
    private String desc;

    WhereStoreMoneyEnum(String mark, String desc) {
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
