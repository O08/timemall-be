package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.TeamInvitedOasis;
import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
import lombok.Data;

import java.util.ArrayList;

@Data
public class TeamInviteVO extends CodeVO {
   private TeamInvitedOasis invited;
}
