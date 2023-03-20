package com.norm.timemall.app.base.enums;

public enum ObjectRecordTagEnum {
    CREATED("1","创建"),
    PUBLISH("2","上线交易"),
    OFFLINE("3","下线交易"),
    IN_USE("4","已使用")
    ;
    private String mark;
    private String desc;

    ObjectRecordTagEnum(String mark, String desc) {
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
