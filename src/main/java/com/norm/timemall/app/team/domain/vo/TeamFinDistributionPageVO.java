package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.TeamFinDistriutionItem;
import lombok.Data;

@Data
public class TeamFinDistributionPageVO extends CodeVO {
    private IPage<TeamFinDistriutionItem> distribution;
}
