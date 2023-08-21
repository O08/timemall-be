package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamPutCommissionDeliverTagDTO {
    @NotBlank(message = "tag required")
    private String tag;
    @NotBlank(message = "deliverId required")
    private String deliverId;
    @NotBlank(message = "commissionId required")
    private String commissionId;
}
