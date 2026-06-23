package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class TeamOasisCollectAccountDTO {

    @NotBlank(message = "oasisId required")
    private String oasisId;

    @NotNull(message = "amount required")
    @Positive(message = "amount must be positive")
    private BigDecimal amount;
}
