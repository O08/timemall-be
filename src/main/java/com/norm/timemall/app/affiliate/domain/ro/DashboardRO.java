package com.norm.timemall.app.affiliate.domain.ro;

import lombok.Data;

@Data
public class DashboardRO {
    private String cellSaleVolume;
    private String clicks;
    private String planSaleVolume;
    private String refundOrders;
    private String sales;
    private String settledPayment;
    private String timespan;
    private String unsettledPayment;
}
