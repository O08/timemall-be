package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisMemberRoleConfigurationRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamFetchOasisMemberRoleConfigurationVO extends CodeVO {

    private ArrayList<TeamFetchOasisMemberRoleConfigurationRO> role;

}
