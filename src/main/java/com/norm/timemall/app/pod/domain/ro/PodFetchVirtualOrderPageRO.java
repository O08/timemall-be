package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;

@Data
public class PodFetchVirtualOrderPageRO {
    private String createAt;
    private String orderNO;
    private String orderId;
    private String productId;
    private String productName;
    private String productPrice;
    private String quantity;
    private String sellerAvatar;
    private String sellerBrandId;
    private String sellerBrandName;
    private String sellerUserId;
    private String tag;
    private String totalFee;
    private String alreadyRefund;
    private String alreadyRemittance;
    private String alreadyPay;
    private String refundReason;
}
