package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemBuyerGetOneOrderInfoRO;
import lombok.Data;

@Data
public class TeamAppRedeemBuyerGetOneOrderInfoVO extends CodeVO {
    private TeamAppRedeemBuyerGetOneOrderInfoRO order;
}
