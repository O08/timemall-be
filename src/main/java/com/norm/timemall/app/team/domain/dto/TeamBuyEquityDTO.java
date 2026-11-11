package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class TeamBuyEquityDTO {
    @NotBlank(message = "periodId required")
    private String periodId;

    @Range(min = 1,max = 1000,message = "shares range in {min} - {max}")
    @NotNull(message = "shares required")
    @Positive(message = "shares must be positive")
    private Integer shares;
}
