package com.norm.timemall.app.team.domain.ro;

import com.norm.timemall.app.team.domain.pojo.TeamFetchOasisMemberRole;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamFetchOasisMemberPageRO {
    private String joinOasisAt;
    private String memberBrandId;
    private String memberName;
    private String memberTitle;
    private String memberUserId;
    private String memberAvatar;
    private ArrayList<TeamFetchOasisMemberRole> roles;

}
