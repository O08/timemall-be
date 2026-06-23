package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemGetProductProfileRO;
import lombok.Data;

@Data
public class TeamAppRedeemGetProductProfileVO extends CodeVO {
    private TeamAppRedeemGetProductProfileRO product;
}
