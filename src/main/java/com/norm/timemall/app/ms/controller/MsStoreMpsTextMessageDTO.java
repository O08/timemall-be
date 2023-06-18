package com.norm.timemall.app.ms.controller;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MsStoreMpsTextMessageDTO {
    @NotBlank(message = "authorId required")
    private String authorId;
    @NotBlank(message = "msgType required")
    private String msgType;
    @NotBlank(message = "msg required")
    private String msg;
}
