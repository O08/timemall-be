package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class MallFetchPromotionInfoRO {
    private String creditPoint;
    private String creditPointTag;
    private String earlyBirdDiscount;
    private String earlyBirdDiscountTag;
    private String repurchaseDiscount;
    private String repurchaseDiscountTag;

}
