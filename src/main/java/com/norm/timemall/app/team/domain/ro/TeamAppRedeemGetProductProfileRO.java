package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamAppRedeemGetProductProfileRO {
    private String historyBuyerOrders;
    private String monthBuyerOrders;
    private String inventory;
    private String price;
    private String productId;
    private String productName;
    private String releaseAt;
    private String salesQuota;
    private String salesQuotaType;
    private String shippingType;
    private String status;
    private String thumbnail;
    private String productCode;
    private String estimatedDeliveryAt;
    private String genreId;
    private String shippingTerm;
    private String warmReminder;
}
