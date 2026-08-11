package com.norm.timemall.app.studio.domain.ro;

import com.norm.timemall.app.studio.domain.pojo.FetchVirtualProductMetaInfoShowCase;
import lombok.Data;
import lombok.experimental.Accessors;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

import java.util.ArrayList;

@Data
@Accessors(chain = true)
public class FetchVirtualProductMetaInfoRO {
    private String deliverAttachment;
    private String deliverNote;
    private String inventory;
    private String productDesc;
    private String productName;
    private String productPrice;
    private String productStatus;
    private String provideInvoice;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String pack;
    private String shippingMethod;

    private ArrayList<FetchVirtualProductMetaInfoShowCase> showcase;
    private ArrayList<String> tags;
    private String thumbnailUrl;
}
