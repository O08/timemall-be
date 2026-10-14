package com.norm.timemall.app.team.domain.pojo;

import com.norm.timemall.app.team.domain.ro.TeamAppGroupChatFetchMemberRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamAppGroupChatFetchMember {

    private ArrayList<TeamAppGroupChatFetchMemberRO> records;

}
