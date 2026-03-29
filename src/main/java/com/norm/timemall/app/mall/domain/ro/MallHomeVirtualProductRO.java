package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class MallHomeVirtualProductRO {
    private String clicks;
    private String createAt;
    private String inventory;
    /**
     * The Id for virtual
     */
    private String productId;
    /**
     * Service name
     */
    private String productName;
    private String productPrice;
    private String salesVolume;

    private String sellerBrandId;

    /**
     * The product Preview Img Uri
     */
    private String thumbnailUrl;
    private String views;
}
