package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamMembershipFetchBuyRecordPageRO;
import lombok.Data;

@Data
public class TeamMembershipFetchBuyRecordPageVO extends CodeVO {
    private IPage<TeamMembershipFetchBuyRecordPageRO> order;
}
