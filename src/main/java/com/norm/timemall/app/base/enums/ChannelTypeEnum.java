package com.norm.timemall.app.base.enums;

public enum ChannelTypeEnum {
    DEFAULT("default","默认");
    private String mark;
    private String desc;

    ChannelTypeEnum(String mark, String desc) {
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
