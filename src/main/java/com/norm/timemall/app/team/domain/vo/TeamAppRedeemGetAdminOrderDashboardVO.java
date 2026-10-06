package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemGetAdminOrderDashboardRO;
import lombok.Data;

@Data
public class TeamAppRedeemGetAdminOrderDashboardVO extends CodeVO {
    private TeamAppRedeemGetAdminOrderDashboardRO dashboard;
}
