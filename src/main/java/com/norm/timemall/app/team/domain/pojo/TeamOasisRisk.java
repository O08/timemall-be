package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

@Data
public class TeamOasisRisk {
    private String oasisId;
    private TeamRiskEntry[] riskEntries;
}
