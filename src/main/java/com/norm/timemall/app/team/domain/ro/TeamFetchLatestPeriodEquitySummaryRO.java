package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamFetchLatestPeriodEquitySummaryRO {
    private Integer period;
    private Integer redemption;
    private Integer shares;
    private Integer sold;
    private Integer totalSponsor;
    private Integer writeOff;
    private String sponsorshipFee;
    private String writeOffFee;
    private String redemptionFee;
    private String status;
    private String periodId;
    private String earningYield;
}
