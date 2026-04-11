package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemAdminGetOneOrderInfoRO;
import lombok.Data;

@Data
public class TeamAppRedeemAdminGetOneOrderInfoVO extends CodeVO {
    private TeamAppRedeemAdminGetOneOrderInfoRO order;
}
