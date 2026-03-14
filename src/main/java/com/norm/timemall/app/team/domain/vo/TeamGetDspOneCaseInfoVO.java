package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamGetDspOneCaseInfoBasicRO;
import com.norm.timemall.app.team.domain.ro.TeamGetDspOneCaseInfoMaterialRO;
import lombok.Data;

@Data
public class TeamGetDspOneCaseInfoVO extends CodeVO {

    private TeamGetDspOneCaseInfoBasicRO general;
    private TeamGetDspOneCaseInfoMaterialRO material;

}
