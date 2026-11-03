package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TeamOasisPointVO extends CodeVO {
    private BigDecimal point;
}
