package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class MallFetchPhysicalProductInfoRO {
    private String productDesc;
    private String price;
    private String productName;
    private String thumbnail;
    private String warmReminder;
    private String inventory;
    private String productId;
    private String status;
    private String sellerName;
    private String sellerUserId;
    private String sellerBrandId;
    private String sellerAvatar;
    private String sellerHandle;
}
