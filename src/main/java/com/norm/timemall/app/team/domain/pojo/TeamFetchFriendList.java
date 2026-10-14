package com.norm.timemall.app.team.domain.pojo;

import com.norm.timemall.app.team.domain.ro.TeamFetchFriendRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamFetchFriendList {
    private ArrayList<TeamFetchFriendRO> records;
}
