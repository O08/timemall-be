package com.norm.timemall.app.team.domain.ro;

import com.norm.timemall.app.team.domain.pojo.TeamDspCaseMaterial;
import lombok.Data;

@Data
public class TeamGetDspOneCaseInfoMaterialRO {
    private TeamDspCaseMaterial[] defendantMaterial;
    private TeamDspCaseMaterial[] informerMaterial;
    private TeamDspCaseMaterial[] peacemakerMaterial;
}
