package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class MallRetrieveBrandProductListPageRO {

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
    /**
     * Brand avatar Image Url
     */
    private String sellerAvatar;
    private String sellerBrandId;
    /**
     * Brand name
     */
    private String sellerName;
    /**
     * The product Preview Img Uri
     */
    private String thumbnailUrl;
    private String views;

    private String clicks;
    private String createAt;
    private String inventory;
}
