package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

import java.util.ArrayList;

@Data
public class MallFetchVirtualProductProfileRO {

    private String inventory;
    private String productDesc;
    private String productName;
    private String productPrice;
    private String productStatus;
    private String provideInvoice;
    private ArrayList<String>  showcase;
    private ArrayList<String> tags;
    private String thumbnailUrl;
    private String shippingMethod;

}
