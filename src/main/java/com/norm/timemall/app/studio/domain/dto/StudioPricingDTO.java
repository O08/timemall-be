package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.studio.domain.pojo.StudioPricing;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class StudioPricingDTO {
    @NotNull(message = "pricing required")
    private StudioPricing pricing;
}
