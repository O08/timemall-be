package com.norm.timemall.app.team.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class TeamAppRedeemAdminGetOneOrderInfoRO {
    private String orderId;
    private String orderNo;

    private String buyerAvatar;
    private String buyerName;
    private String buyerUserId;
    private String deliveryMaterial;

    @FieldEncrypt(algorithm = Algorithm.AES)
    private String deliveryNote;

    private String productName;
    private String productPrice;
    private String productThumbnail;
    private String quantity;
    private String status;
    private String total;
    private String createAt;

}
