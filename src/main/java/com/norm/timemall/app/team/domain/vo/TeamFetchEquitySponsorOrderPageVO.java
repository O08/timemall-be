package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquitySponsorOrderPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamFetchEquitySponsorOrderPageVO extends CodeVO {
    private IPage<TeamFetchEquitySponsorOrderPageRO> order;
}
