package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamOasisRisk {
    private String oasisId;
    private ArrayList<TeamRiskEntry> riskEntries;
}
