package com.norm.timemall.app.team.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppMeetrAttendee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.norm.timemall.app.team.domain.dto.TeamAppMeetrFetchEventAttendeesPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppMeetrFetchMemberAttendancesPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchEventAttendeesPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchMemberAttendancesPageRO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * (app_meetr_attendee)数据Mapper
 *
 * @author kancy
 * @since 2026-06-17 19:31:48
 * @description 由 Mybatisplus Code Generator 创建
*/
@Mapper
public interface TeamAppMeetrAttendeeMapper extends BaseMapper<AppMeetrAttendee> {
    IPage<TeamAppMeetrFetchEventAttendeesPageRO> selectEventAttendeesPage(IPage<TeamAppMeetrFetchEventAttendeesPageRO> page, @Param("dto") TeamAppMeetrFetchEventAttendeesPageDTO dto);

    IPage<TeamAppMeetrFetchMemberAttendancesPageRO> selectMemberAttendancesPage(IPage<TeamAppMeetrFetchMemberAttendancesPageRO> page, @Param("dto") TeamAppMeetrFetchMemberAttendancesPageDTO dto, @Param("currentBrandId") String currentBrandId);
}
