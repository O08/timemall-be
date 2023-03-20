package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.TeamBrandBank;
import lombok.Data;

@Data
public class TeamBrandBankVO extends CodeVO {
    private TeamBrandBank bank;
}
