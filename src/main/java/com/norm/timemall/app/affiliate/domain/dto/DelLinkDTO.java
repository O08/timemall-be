package com.norm.timemall.app.affiliate.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DelLinkDTO {
    @NotBlank(message = "linkMarketingId required")
    private String linkMarketingId;
}
