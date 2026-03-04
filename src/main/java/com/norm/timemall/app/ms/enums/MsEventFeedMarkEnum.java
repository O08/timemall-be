package com.norm.timemall.app.ms.enums;

public enum MsEventFeedMarkEnum {
    CREATED("created","创建"),
    PROCESSED("processed","已处理");
    private String mark;
    private String desc;

    MsEventFeedMarkEnum(String mark, String desc) {
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
