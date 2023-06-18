package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StudioMpsPaperDeliverLeaveMsgDTO {
    @NotBlank(message = "msg required")
    private String msg;
    @NotBlank(message = "deliverId required")
    private String deliverId;
}
