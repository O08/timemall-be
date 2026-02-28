package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.TeamDspCaseMaterial;
import lombok.Data;

@Data
public class TeamGetDspCaseMaterialVO extends CodeVO {
    private TeamDspCaseMaterial[] material;
}
