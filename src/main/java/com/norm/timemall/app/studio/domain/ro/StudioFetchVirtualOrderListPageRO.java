package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioFetchVirtualOrderListPageRO {
    private String buyerAvatar;
    private String buyerBrandId;
    private String buyerBrandName;
    private String buyerUserId;
    private String createAt;
    private String orderNO;
    private String orderId;
    private String productId;
    private String productName;
    private String productPrice;
    private String quantity;
    private String tag;
    private String totalFee;
    private String refundReason;
    private String alreadyRemittance;
    private String alreadyRefund;
}
