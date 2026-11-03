package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppFbFetchFeedRO;
import lombok.Data;

@Data
public class TeamAppFbFetchFeedVO extends CodeVO {
    private TeamAppFbFetchFeedRO feed;
}
