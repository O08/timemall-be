package com.norm.timemall.app.team.domain.ro;

import com.norm.timemall.app.team.domain.pojo.TeamMembershipFetchSellingTierFeature;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamMembershipFetchSellingTierRO {
    private String endAt;
    private ArrayList<TeamMembershipFetchSellingTierFeature> features;
    private String price;
    private String thumbnail;
    private String tierDescription;
    private String tierId;
    private String tierName;
}
