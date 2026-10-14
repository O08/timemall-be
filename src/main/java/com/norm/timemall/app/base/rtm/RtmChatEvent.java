package com.norm.timemall.app.base.rtm;

public enum RtmChatEvent {
    PING("PING"),
    CONNECTED("CONNECTED"),
    JOIN_GROUP("JOIN_GROUP"),
    NOTIFY_PULL("NOTIFY_PULL"),
    NEW_MSG_NOTIFY("NEW_MSG_NOTIFY"), // 推信号

    NOTIFY_PULL_STATELESS("NOTIFY_PULL_STATELESS"),
    NEW_MSG_NOTIFY_STATELESS("NEW_MSG_NOTIFY_STATELESS"),
    FORCE_OFFLINE("FORCE_OFFLINE");  // 强制下线信号
    private final String value;
    RtmChatEvent(String value) { this.value = value; }
    public String getValue() { return value; }
}
