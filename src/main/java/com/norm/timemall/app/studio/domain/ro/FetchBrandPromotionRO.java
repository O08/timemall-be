package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class FetchBrandPromotionRO {
    private String creditPoint;
    private String creditPointCnt;
    private String creditPointTag;
    private String earlyBirdDiscount;
    private String earlyBirdDiscountCnt;
    private String earlyBirdDiscountTag;
    private String repurchaseDiscount;
    private String repurchaseDiscountCnt;
    private String repurchaseDiscountTag;
}
