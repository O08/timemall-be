package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamMembershipFetchSellingTierRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamMembershipFetchSellingTierVO extends CodeVO {
 private ArrayList<TeamMembershipFetchSellingTierRO> tier;
}
