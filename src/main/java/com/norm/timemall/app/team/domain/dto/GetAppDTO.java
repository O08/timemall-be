package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class GetAppDTO {
    @NotBlank(message = "appId required")
    private String appId;
    @NotBlank(message = "oasisId required")
    private String oasisId;
}
