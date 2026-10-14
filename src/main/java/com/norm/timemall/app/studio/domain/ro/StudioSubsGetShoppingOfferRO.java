package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioSubsGetShoppingOfferRO {
    private String description;
    private String discountAmount;
    private String discountPercentage;
    private String forPlanId;
    private String forProductId;
    private String name;
    private String offerType;
    private String status;
    private String used;
    private String capacity;
    private String claims;
}
