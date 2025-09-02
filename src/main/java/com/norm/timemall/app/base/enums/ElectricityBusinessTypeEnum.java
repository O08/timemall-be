package com.norm.timemall.app.base.enums;

public enum ElectricityBusinessTypeEnum {
    TOP_UP("top_up","top up")
    ;
    private String mark;
    private String desc;

    ElectricityBusinessTypeEnum(String mark, String desc) {
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
