package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisMemberPageRO;
import lombok.Data;

@Data
public class TeamFetchOasisMemberPageVO extends CodeVO {
   private IPage<TeamFetchOasisMemberPageRO> member;
}
