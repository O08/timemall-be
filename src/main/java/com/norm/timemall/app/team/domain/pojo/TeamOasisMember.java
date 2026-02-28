package com.norm.timemall.app.team.domain.pojo;

import com.norm.timemall.app.team.domain.ro.TeamOasisMemberRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamOasisMember {
    private ArrayList<TeamOasisMemberRO> records;
}
