package com.norm.timemall.app.base.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeliverLeaveMsgDTO {
    @NotBlank(message = "msg required")
    private String msg;
    @NotBlank(message = "deliverId required")
    private String deliverId;
}
