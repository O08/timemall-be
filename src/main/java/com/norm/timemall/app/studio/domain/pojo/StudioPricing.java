package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class StudioPricing {
    @NotNull(message = "day is required")
    private BigDecimal day;
    private BigDecimal week;
    private BigDecimal month;
    private BigDecimal quarter;
    private BigDecimal year;
    @NotNull(message = "hour is required")
    private BigDecimal hour;
    private BigDecimal minute;
    private BigDecimal second;
}
