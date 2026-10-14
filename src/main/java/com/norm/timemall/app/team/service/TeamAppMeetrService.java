package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.AppMeetrEvent;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrDiscoveryEventsPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchEventAttendeesPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchMemberAttendancesPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchOneEventInfoRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamAppMeetrService {
    IPage<TeamAppMeetrDiscoveryEventsPageRO> discoveryEvents(TeamAppMeetrDiscoveryEventsPageDTO dto);

    void newEvent(TeamAppMeetrNewEventsDTO dto,String thumbnailUri,String oasisId);

    TeamAppMeetrFetchOneEventInfoRO fetchEventInfo(String eventId);

    void editEvent(TeamAppMeetrEditEventsDTO dto);

    void deleteEvent(String eventId);

    AppMeetrEvent fetchEventAllInfo(String eventId);

    void reserveEvent(String eventId);

    void delOneAttendance(String attendanceId);

    IPage<TeamAppMeetrFetchEventAttendeesPageRO> fetchEventAttendees(TeamAppMeetrFetchEventAttendeesPageDTO dto);

    IPage<TeamAppMeetrFetchMemberAttendancesPageRO> fetchMemberAttendances(TeamAppMeetrFetchMemberAttendancesPageDTO dto);

    void changeEventThumbnail(String eventId, String thumbnailUri);

    void changeChannelSetting(TeamAppMeetrChangeChannelSettingDTO dto);
}
