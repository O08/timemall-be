package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class TeamObjPricingDTO {
    @NotBlank(message = "objId required")
    private String objId;
    @Positive(message = "price required and positive")
    @NotNull(message = "price is required and must be number")
    private BigDecimal price;
}
