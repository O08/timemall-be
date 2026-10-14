package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamFetchLatestPeriodEquitySummaryRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamFetchLatestPeriodEquitySummaryVO extends CodeVO {
    private TeamFetchLatestPeriodEquitySummaryRO summary;
}
