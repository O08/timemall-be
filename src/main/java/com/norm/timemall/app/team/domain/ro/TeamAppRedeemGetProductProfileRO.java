package com.norm.timemall.app.team.domain.ro;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class TeamAppRedeemGetProductProfileRO {
    private String historyBuyerOrders;
    private String monthBuyerOrders;
    private String inventory;
    private String price;
    private String productId;
    private String productName;
    private String salesQuota;
    private String salesQuotaType;
    private String shippingType;
    private String status;
    private String thumbnail;
    private String productCode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date releaseAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date estimatedDeliveryAt;


    private String genreId;
    private String shippingTerm;
    private String warmReminder;
    private String genreName;
    private String soldOrders;
    private String views;
}
