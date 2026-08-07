package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioGetShoppingSubscriptionMetaInfoRO {
    private String productId;
    private String productName;
    private String sellerHandle;
    private String sellerPdOasisId;
    private String sellerUserId;
    private String sellerBrandId;

}
