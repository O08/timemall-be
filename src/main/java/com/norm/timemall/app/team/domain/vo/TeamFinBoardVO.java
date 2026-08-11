package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamFinBoardRO;
import lombok.Data;

@Data
public class TeamFinBoardVO extends CodeVO {
    private TeamFinBoardRO billboard;
}
