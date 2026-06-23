package com.norm.timemall.app.base.enums;

public enum OasisJoinTagEnum {
    CREATED("1","创建"),
    ACCEPT("2","接受邀请"),
    DENY("3","拒绝邀请");
    private String mark;
    private String desc;

    OasisJoinTagEnum(String mark, String desc) {
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
