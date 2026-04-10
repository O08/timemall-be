package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamFetchEquitySponsorOrderPageRO {
    private String orderId;
    private String donationAmount;
    private String redemptionAmount;
    private String shares;
    private String earningYield;
    private String createAt;
}
