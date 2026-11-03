package com.norm.timemall.app.base.pojo;

import lombok.Data;

@Data
public class DefaultPhysicalOrderItem {
    private String orderId;
    private String orderItemId;
    private String productId;
    private String productName;
    private String productPrice;
    private String productThumbnail;
    private String quantity;
    private String refundReason;
    private String splitPayAmount;
    private String splitPromotionAmount;
    private String status;
}
