package com.norm.timemall.app.team.domain.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.CodeVO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrDiscoveryEventsPageRO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamAppMeetrDiscoveryEventsPageVO extends CodeVO {
   private IPage<TeamAppMeetrDiscoveryEventsPageRO> event;
}
