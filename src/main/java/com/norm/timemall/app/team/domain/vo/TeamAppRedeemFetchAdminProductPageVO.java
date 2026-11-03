package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppRedeemFetchAdminProductPageRO;
import lombok.Data;

@Data
public class TeamAppRedeemFetchAdminProductPageVO extends CodeVO {
    private IPage<TeamAppRedeemFetchAdminProductPageRO> product;
}
