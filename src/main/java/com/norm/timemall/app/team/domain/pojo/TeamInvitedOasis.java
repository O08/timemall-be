package com.norm.timemall.app.team.domain.pojo;

import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamInvitedOasis {
    private ArrayList<TeamInviteRO> records;
}
