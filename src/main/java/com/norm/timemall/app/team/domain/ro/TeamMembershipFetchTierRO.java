package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

@Data
public class TeamMembershipFetchTierRO {
    private String members;
    private String od;
    private String price;
    private String status;
    private String subscribeRoleId;
    private String thumbnail;
    private String tierDescription;
    private String tierId;
    private String tierName;
}
