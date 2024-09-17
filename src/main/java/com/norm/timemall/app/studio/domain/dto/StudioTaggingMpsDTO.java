package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class StudioTaggingMpsDTO {
    @NotBlank(message = "tag required")
    private String tag;
    @NotBlank(message = "mpsId required")
    private String mpsId;
    @NotBlank(message = "chainId required")
    private String chainId;

}
