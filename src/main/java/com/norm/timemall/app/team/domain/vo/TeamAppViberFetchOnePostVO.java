package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppViberFetchOnePostRO;
import lombok.Data;

@Data
public class TeamAppViberFetchOnePostVO extends CodeVO {
    private TeamAppViberFetchOnePostRO post;
}
