package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudioPricing {
    private BigDecimal day;
    private BigDecimal week;
    private BigDecimal month;
    private BigDecimal quarter;
    private BigDecimal year;
    private BigDecimal hour;
    private BigDecimal minute;
    private BigDecimal second;
}
