package com.norm.timemall.app.team.domain.pojo;

import com.norm.timemall.app.team.domain.ro.TeamFetchCommissionDeliverRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamFetchCommissionDeliver {
    private ArrayList<TeamFetchCommissionDeliverRO> records;
    private String role;
}
