package com.norm.timemall.app.affiliate.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class NewLinkMarketingDTO {
    @NotBlank(message = "cellId required")
    private String cellId;
    @NotBlank(message = "outreachChannelId required")
    private String outreachChannelId;
}
