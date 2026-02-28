package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamFetchRoleChannelRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamFetchRoleChannelVO extends CodeVO {
    private ArrayList<TeamFetchRoleChannelRO> channel;
}
