package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchGuideRO;
import lombok.Data;

@Data
public class TeamAppFbFetchGuideVO extends CodeVO {
    private TeamAppFbFetchGuideRO guide;
}
