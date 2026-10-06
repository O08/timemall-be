package com.norm.timemall.app.ms.enums;

public enum MsEventFeedSceneEnum {
    POD("pod","Pod Event"),
    STUDIO("studio","Studio Event");
    private String mark;
    private String desc;

    MsEventFeedSceneEnum(String mark, String desc) {
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
