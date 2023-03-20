package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamAcceptOasisTaskDTO {
    @NotBlank(message = "commissionId required")
    private String commissionId;
    @NotBlank(message = "brandId required")
    private String brandId;

}
