package com.norm.timemall.app.ms.enums;

public enum MsEventFeedCodeEnum {
    UPDATE_EVENT_FEED_MARK("n-0001","更新 event_feed 表 mark"),
    SEND_STUDIO_MESSAGE_NOTICE("n-0002","通知 studio 用户有新消息"),
    SEND_POD_MESSAGE_NOTICE("n-0003","通知 pod 用户有新消息");
    private String mark;
    private String desc;

    MsEventFeedCodeEnum(String mark, String desc) {
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
