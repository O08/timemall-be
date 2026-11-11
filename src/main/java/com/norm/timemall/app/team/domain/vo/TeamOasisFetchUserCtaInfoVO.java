package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamOasisFetchUserCtaInfoRO;
import lombok.Data;

@Data
public class TeamOasisFetchUserCtaInfoVO extends CodeVO {
    private TeamOasisFetchUserCtaInfoRO cta;
}
