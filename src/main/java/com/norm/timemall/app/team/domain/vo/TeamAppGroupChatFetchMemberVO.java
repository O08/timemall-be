package com.norm.timemall.app.team.domain.vo;

import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.pojo.TeamAppGroupChatFetchMember;
import lombok.Data;

@Data
public class TeamAppGroupChatFetchMemberVO extends CodeVO {
    private TeamAppGroupChatFetchMember member;
}
