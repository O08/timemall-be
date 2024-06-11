package com.norm.timemall.app.mall.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AffiliateDTO {
    @NotBlank(message = "influencer is required")
    private String influencer;
    @NotBlank(message = "chn is required")
    private String chn;
    @NotBlank(message = "market is required")
    private String market;
}
