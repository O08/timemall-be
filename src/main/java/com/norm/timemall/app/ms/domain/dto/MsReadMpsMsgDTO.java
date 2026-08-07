package com.norm.timemall.app.ms.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class MsReadMpsMsgDTO {
    @NotBlank(message = "targetId required")
    private String targetId;
}
