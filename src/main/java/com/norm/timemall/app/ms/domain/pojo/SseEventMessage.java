package com.norm.timemall.app.ms.domain.pojo;

import lombok.Data;

@Data
public class SseEventMessage {
    private String scene;
    private String handlerId;
    private String msg;
    private String from;
}
