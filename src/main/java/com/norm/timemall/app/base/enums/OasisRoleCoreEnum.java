package com.norm.timemall.app.base.enums;

public enum OasisRoleCoreEnum {
    ADMIN("admin","管理员"),
    PUBLIC("public","全部成员可见");
    private String mark;
    private String desc;

    OasisRoleCoreEnum(String mark, String desc) {
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
