package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisBalanceTrendRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamFetchOasisBalanceTrendVO extends CodeVO {
    private List<TeamFetchOasisBalanceTrendRO> chart;

}
