package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.math.BigDecimal;

@Data
public class TeamRegisterNewPeriodEquityDTO {
    @NotBlank(message = "oasisId required")
    private String oasisId;

    @Range(min = 1,max = 5,message = "earningYield range in {min} - {max}")
    @NotNull(message = "earningYield required")
    @Positive(message = "earningYield must be positive")
    private BigDecimal earningYield;

    @Range(min = 1,max = 100,message = "price range in {min} - {max}")
    @NotNull(message = "price required")
    @Positive(message = "price must be positive")
    private BigDecimal price;

    @Range(min = 10,max = 200000,message = "shares range in {min} - {max}")
    @NotNull(message = "shares required")
    @Positive(message = "shares must be positive")
    private Integer shares;
}
