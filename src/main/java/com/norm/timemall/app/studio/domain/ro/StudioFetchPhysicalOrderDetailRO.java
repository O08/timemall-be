package com.norm.timemall.app.studio.domain.ro;

import com.norm.timemall.app.base.pojo.DefaultPhysicalOrderItem;
import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

import java.util.List;

@Data
public class StudioFetchPhysicalOrderDetailRO {
    private String buyerAvatar;
    private String buyerBrandId;
    private String buyerName;
    private String buyerUserId;
    private String courierPhone;
    private String createAt;
    private List<DefaultPhysicalOrderItem> items;
    private String orderId;
    private String orderStatus;
    private String payAmount;
    private String paymentStatus;
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
