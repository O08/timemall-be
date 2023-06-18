package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StudioFetchMpsTemplateAllDTO {
    @NotBlank(message = "chainId required")
    private String chainId;
}
