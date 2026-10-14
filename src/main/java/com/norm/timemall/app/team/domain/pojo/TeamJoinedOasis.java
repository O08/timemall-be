package com.norm.timemall.app.team.domain.pojo;

import com.norm.timemall.app.team.domain.ro.TeamJoinedRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamJoinedOasis {
    private ArrayList<TeamJoinedRO> records;
}
