package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class TeamAppRedeemBuyerGetOneOrderInfoRO {
    private String createAt;
    private String deliveryMaterial;

    @FieldEncrypt(algorithm = Algorithm.AES)
    private String deliveryNote;

    private String orderId;
    private String orderNo;
    private String productName;
    private String productPrice;
    private String productThumbnail;
    private String quantity;
    private String sellerAvatar;
    private String sellerName;
    private String sellerUserId;
    private String status;
    private String total;
}
