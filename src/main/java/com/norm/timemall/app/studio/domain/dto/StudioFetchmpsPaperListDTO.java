package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StudioFetchmpsPaperListDTO {
    @NotBlank(message = "mpsId required")
    private String mpsId;
}
