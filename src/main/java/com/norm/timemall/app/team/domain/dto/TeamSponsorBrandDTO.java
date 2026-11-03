package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

@Data
public class TeamSponsorBrandDTO {
    @NotNull(message = "amount required")
    @Positive(message = "amount must be positive")
    @Min(value = 1, message = "amount must be at least 1")
    @Max(value = 999, message = "amount cannot exceed 999")
    private BigDecimal amount;

    @NotBlank(message = "sponsoreeBrandId required")
    private String sponsoreeBrandId;

    @NotBlank(message = "message required")
    @Length(message = "message length must in range {min}-{max}",min = 1,max = 300)
    private String message;
}
