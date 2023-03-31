package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamObj2RO;
import lombok.Data;

@Data
public class TeamObjInfoVO extends CodeVO {
    private TeamObj2RO obj;
}
