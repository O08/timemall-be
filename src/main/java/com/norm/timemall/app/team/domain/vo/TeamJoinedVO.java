package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.TeamJoinedOasis;
import lombok.Data;

@Data
public class TeamJoinedVO extends CodeVO {
    private TeamJoinedOasis joined;
}
