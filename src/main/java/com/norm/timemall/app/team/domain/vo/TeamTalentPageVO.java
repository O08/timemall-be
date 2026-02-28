package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamTalentRO;
import lombok.Data;

@Data
public class TeamTalentPageVO extends CodeVO {
    private IPage<TeamTalentRO> talent;
}
