package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class TeamAppRedeemGetAdminOrderPageRO {


    private String orderId;
    private String orderNo;
    private String productName;
    private String productThumbnail;
    private String quantity;
    private String refunded;
    private String alreadyPay;

    @FieldEncrypt(algorithm = Algorithm.AES)
    private String shippingAddress;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String shippingEmail;

    @FieldEncrypt(algorithm = Algorithm.AES)
    private String consignee;

    @FieldEncrypt(algorithm = Algorithm.AES)
    private String deliveryNote;
    private String deliveryMaterial;

    private String shippingType;
    private String status;
    private String total;
    private String buyerName;
    private String buyerAvatar;
    private String buyerUserId;
    private String createAt;



}
