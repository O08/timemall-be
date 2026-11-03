package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioFetchPhysicalOrderPageRO {
    private String buyerAvatar;
    private String buyerBrandId;
    private String buyerName;
    private String buyerUserId;
    private String createAt;
    private String orderId;
    private String orderStatus;
    private String payAmount;
    private String paymentStatus;
}
