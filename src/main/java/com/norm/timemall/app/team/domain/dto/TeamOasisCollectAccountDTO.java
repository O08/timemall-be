package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class TeamOasisCollectAccountDTO {
    @NotBlank(message = "oasisId required")
    private String oasisId;
    @Positive(message = "amount must be positive")
    private BigDecimal amount;
}
