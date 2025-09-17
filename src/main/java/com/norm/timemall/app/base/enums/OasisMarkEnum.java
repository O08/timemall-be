package com.norm.timemall.app.base.enums;

public enum OasisMarkEnum {
    CREATED("1","创建"),
    PUBLISH("2","发布"),
    CHAOS("3","无管理员状态"),
    BLOCKED("4","封锁")
    ;
    private String mark;
    private String desc;

    OasisMarkEnum(String mark, String desc) {
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
