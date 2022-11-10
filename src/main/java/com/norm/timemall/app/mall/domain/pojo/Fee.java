package com.norm.timemall.app.mall.domain.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * cell pricing
 */
@Data
public class Fee {
    // The price for cell sbu
    private BigDecimal price;
    // Standard Billing Unit SBU
    private String sbu;
}
