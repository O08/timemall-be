package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import lombok.Data;

@Data
public class TeamTransVO extends CodeVO {
    private TeamTrans trans;
}
