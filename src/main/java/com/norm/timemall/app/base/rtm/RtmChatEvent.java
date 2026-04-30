package com.norm.timemall.app.base.rtm;

public enum RtmChatEvent {
    PING("PING"),
    CONNECTED("CONNECTED"),
    JOIN_GROUP("JOIN_GROUP"),
    NOTIFY_PULL("NOTIFY_PULL"),
    NEW_MSG_NOTIFY("FETCH_NEW_DATA"), // 推信号
    FORCE_OFFLINE("FORCE_OFFLINE");  // 强制下线信号
    private final String value;
    RtmChatEvent(String value) { this.value = value; }
    public String getValue() { return value; }
}
