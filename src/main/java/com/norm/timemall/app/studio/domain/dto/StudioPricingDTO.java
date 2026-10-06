package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.studio.domain.pojo.StudioPricing;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Data
public class StudioPricingDTO {
    @NotNull(message = "pricing required")
    @Valid
    private StudioPricing pricing;
}
