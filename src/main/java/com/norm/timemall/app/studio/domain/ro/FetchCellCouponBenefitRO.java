package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FetchCellCouponBenefitRO {
    /**
     * 1 can,0 no
     */
    private String canUseEarlyBirdCoupon;
    /**
     * 1 can,0 no
     */
    private String canUseRepurchaseCoupon;
    private BigDecimal creditPoint;
}
