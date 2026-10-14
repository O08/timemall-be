package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CouponCreditPointBenefitRO {
    private BigDecimal creditPoint;
    /**
     * 1 already,0 no
     */
    private String alreadyGetCreditPoint;
}
