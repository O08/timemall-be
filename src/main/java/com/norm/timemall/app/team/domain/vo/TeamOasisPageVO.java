package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamOasisRO;
import lombok.Data;

@Data
public class TeamOasisPageVO extends CodeVO {
    private IPage<TeamOasisRO> oasis;
}
