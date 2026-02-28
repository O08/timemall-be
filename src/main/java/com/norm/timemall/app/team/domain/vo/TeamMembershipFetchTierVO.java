package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamMembershipFetchTierRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamMembershipFetchTierVO extends CodeVO {
    private ArrayList<TeamMembershipFetchTierRO> tier;
}
