package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchMemberAttendancesPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamAppMeetrFetchMemberAttendancesPageVO extends CodeVO {
    private IPage<TeamAppMeetrFetchMemberAttendancesPageRO> attendance;
}
