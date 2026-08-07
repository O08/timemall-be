package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamAppRedeemGetAdminOrderDashboardRO {
    private String buyers;
    private String shippingOrders;
    private String soldOrders;
    private String totalSales;
}
