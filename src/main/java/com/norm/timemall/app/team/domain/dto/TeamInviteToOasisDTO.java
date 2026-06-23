package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class TeamInviteToOasisDTO {
    @NotBlank(message = "oasisId required")
    private String oasisId;
    @NotBlank(message = "brandId required")
    private String brandId;
}
