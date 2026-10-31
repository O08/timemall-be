package com.norm.timemall.app.pod.domain.ro;

import com.norm.timemall.app.base.pojo.DefaultPhysicalOrderItem;
import lombok.Data;
import mybatis.mate.annotation.Algorithm;
import mybatis.mate.annotation.FieldEncrypt;

import java.util.List;

@Data
public class PodFetchPhysicalOrderDetailRO {
    private String orderId;
    private String orderStatus;
    private String payAmount;
    private String paymentStatus;
    private String createAt;
    private String sellerBrandId;
    private String sellerUserId;
    private String sellerName;
    private String sellerAvatar;
    private String sellerHandle;
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
    @FieldEncrypt(algorithm = Algorithm.AES)
    private String courierPhone;
    private List<DefaultPhysicalOrderItem> items;
}
