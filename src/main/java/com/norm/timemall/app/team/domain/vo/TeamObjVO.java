package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamObjRO;
import lombok.Data;

@Data
public class TeamObjVO extends CodeVO {
    private TeamObjRO obj;
}
