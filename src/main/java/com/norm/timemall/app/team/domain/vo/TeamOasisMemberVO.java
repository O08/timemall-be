package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.TeamOasisMember;
import lombok.Data;

@Data
public class TeamOasisMemberVO  extends CodeVO {
    private TeamOasisMember member;
}
