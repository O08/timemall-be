package com.norm.timemall.app.base.enums;

public enum FeedbackMarkEnum {
    CREATED("1","创建"),
    PROCESSING("2","处理中"),
    END("3","处理结束")
    ;
    private String mark;
    private String desc;

    FeedbackMarkEnum(String mark, String desc) {
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
