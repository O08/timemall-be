package com.norm.timemall.app.team.mapper;

import com.norm.timemall.app.base.mo.AppMeetrEvent;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.team.domain.dto.TeamAppMeetrDiscoveryEventsPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrDiscoveryEventsPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchOneEventInfoRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (app_meetr_event)数据Mapper
 *
 * @author kancy
 * @since 2026-06-17 19:31:48
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppMeetrEventMapper extends BaseMapper<AppMeetrEvent> {

    IPage<TeamAppMeetrDiscoveryEventsPageRO> selectPageByQ(IPage<TeamAppMeetrDiscoveryEventsPageRO> page, @Param("dto") TeamAppMeetrDiscoveryEventsPageDTO dto, @Param("currentBrandId") String currentBrandId);

    TeamAppMeetrFetchOneEventInfoRO selectOneEventById(@Param("eventId") String eventId, @Param("currentBrandId") String currentBrandId);
}
