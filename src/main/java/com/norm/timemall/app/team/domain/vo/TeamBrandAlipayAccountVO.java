package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.TeamBrandAlipayAccount;
import lombok.Data;

@Data
public class TeamBrandAlipayAccountVO extends CodeVO {
    private TeamBrandAlipayAccount alipay;
}
