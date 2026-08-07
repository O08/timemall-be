package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamGetChannelOwnedByRoleRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamGetChannelOwnedByRoleVO extends CodeVO {
    private ArrayList<TeamGetChannelOwnedByRoleRO> channel;
}
