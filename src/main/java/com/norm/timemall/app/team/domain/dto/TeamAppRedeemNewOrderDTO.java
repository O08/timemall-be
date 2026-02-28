package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class TeamAppRedeemNewOrderDTO {
    @NotNull(message = "quantity is required and must be number")
    @Positive(message = "quantity value should be positive.")
    private Integer quantity;

    @NotBlank(message = "productId is required")
    private String productId;
}
