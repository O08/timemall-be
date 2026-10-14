package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamMembershipFetchOpenTierOrderPageRO {
    private String buyerAvatar;
    private String buyerName;
    private String orderId;
    private String status;
    private String tierName;
    private String total;
    private String cardType;
}
