package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class MallRetrieveProductListPageRO {

    private String brandId;
    /**
     * Brand Cover Image Url
     */
    private String avatar;
    /**
     * Brand name
     */
    private String brand;
    private String brandMark;
    /**
     * 是否开启蓝标
     */
    private String enableBlue;
    /**
     * The Id for product
     */
    private String productId;
    /**
     * product name
     */
    private String productName;
    private double productPrice;
    private String salesVolume;
    /**
     * The product thumbnail Img Uri
     */
    private String thumbnailUrl;
    private String views;
}
