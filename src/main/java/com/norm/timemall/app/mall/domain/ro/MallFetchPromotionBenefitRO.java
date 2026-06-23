package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MallFetchPromotionBenefitRO {
    /**
     * 1 can,0 no
     */
    private String canUseEarlyBirdCoupon;
    /**
     * 1 can,0 no
     */
    private String canUseRepurchaseCoupon;
    private BigDecimal creditPoint;
    /**
     * 1 already,0 no
     */
    private String alreadyGetCreditPoint;
}
