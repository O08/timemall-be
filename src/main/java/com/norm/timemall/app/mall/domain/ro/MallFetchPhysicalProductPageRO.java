package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class MallFetchPhysicalProductPageRO {
    private String price;

    private String productId;

    private String productName;

    private String thumbnail;

    private String inventory;
}
