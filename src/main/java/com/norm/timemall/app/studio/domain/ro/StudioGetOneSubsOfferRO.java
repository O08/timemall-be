package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioGetOneSubsOfferRO {
    private Integer capacity;
    private String userClaimId;
    private Integer claims;
    private String description;
    private String discountAmount;
    private String discountPercentage;
    private String name;
    private String offerId;
    private String sellerAvatar;
    private String sellerHandle;
    private String sellerName;
    private String status;
    private String claimChannel;
}
