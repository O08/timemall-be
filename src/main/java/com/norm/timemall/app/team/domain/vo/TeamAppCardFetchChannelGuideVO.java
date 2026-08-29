package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppCardFetchChannelGuideRO;
import lombok.Data;

@Data
public class TeamAppCardFetchChannelGuideVO  extends CodeVO {
    private TeamAppCardFetchChannelGuideRO guide;
}
