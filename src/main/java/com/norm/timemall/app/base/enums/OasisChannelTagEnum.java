package com.norm.timemall.app.base.enums;

public enum OasisChannelTagEnum {
    CREATED("0","创建"),
    ONLINE("1","上线"),
    OFFLINE("2","下线")
    ;
    private String mark;
    private String desc;

    OasisChannelTagEnum(String mark, String desc) {
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
