package com.norm.timemall.app.base.enums;

public enum MpsChainTagEnum {
    PUBLISH("1","运行中"),
    OFFLINE("2","停运")
    ;
    private String mark;
    private String desc;

    MpsChainTagEnum(String mark, String desc) {
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
