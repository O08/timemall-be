package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class TeamAppRedeemGetAdminOrderPageRO {


    private String orderId;
    private String orderNo;
    private String productName;
    private String quantity;
    private String refunded;

    @FieldEncrypt(algorithm = Algorithm.AES)
    private String shippingAddress;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String shippingEmail;

    @FieldEncrypt(algorithm = Algorithm.AES)
    private String consignee;

    private String shippingType;
    private String status;
    private String total;
    private String buyerName;
    private String buyerAvatar;
    private String buyerUserId;
    private String createAt;



}
