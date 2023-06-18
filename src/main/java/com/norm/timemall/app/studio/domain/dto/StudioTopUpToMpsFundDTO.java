package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class StudioTopUpToMpsFundDTO {
    @NotNull(message = "amount is required")
    @Positive(message = "amount required and must be positive")
    private BigDecimal amount;
    @NotBlank(message = "mpsfundId required")
    private String mpsfundId;
}
