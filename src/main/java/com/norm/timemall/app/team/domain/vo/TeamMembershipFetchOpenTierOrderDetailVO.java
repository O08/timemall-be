package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamMembershipFetchOpenTierOrderDetailRO;
import lombok.Data;

@Data
public class TeamMembershipFetchOpenTierOrderDetailVO extends CodeVO {
    private TeamMembershipFetchOpenTierOrderDetailRO order;
}
