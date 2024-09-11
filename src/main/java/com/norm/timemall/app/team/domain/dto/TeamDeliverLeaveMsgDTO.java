package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
@Data
public class TeamDeliverLeaveMsgDTO {
    @NotBlank(message = "msg required")
    private String msg;
    @NotBlank(message = "deliverId required")
    private String deliverId;
    @NotBlank(message = "commissionId required")
    private String commissionId;
}
