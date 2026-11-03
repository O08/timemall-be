package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

@Data
public class StudioFetchPhysicalOrderDeliveryInfoRO {
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String courierPhone;
    private String orderId;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String recipient;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String recipientPhone;
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String shippingAddress;
    private String shippingMethod;
    private String shippingNote;
    private String shippingNumber;
    private String shippingStatus;
}
