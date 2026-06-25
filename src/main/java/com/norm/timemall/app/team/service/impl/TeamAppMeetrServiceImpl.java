package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.AppMeetrEventStatusEnum;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppMeetrAttendee;
import com.norm.timemall.app.base.mo.AppMeetrEvent;
import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.pojo.AppMeetrChannelGuide;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrDiscoveryEventsPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchEventAttendeesPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchMemberAttendancesPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchOneEventInfoRO;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.team.mapper.TeamAppMeetrAttendeeMapper;
import com.norm.timemall.app.team.mapper.TeamAppMeetrEventMapper;
import com.norm.timemall.app.team.mapper.TeamOasisChannelMapper;
import com.norm.timemall.app.team.service.TeamAppMeetrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TeamAppMeetrServiceImpl implements TeamAppMeetrService {
    @Autowired
    private TeamAppMeetrEventMapper teamAppMeetrEventMapper;
    @Autowired
    private TeamAppMeetrAttendeeMapper teamAppMeetrAttendeeMapper;

    @Autowired
    private TeamOasisChannelMapper teamOasisChannelMapper;


    @Autowired
    private Gson gson;

    @Override
    public IPage<TeamAppMeetrDiscoveryEventsPageRO> discoveryEvents(TeamAppMeetrDiscoveryEventsPageDTO dto) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        IPage<TeamAppMeetrDiscoveryEventsPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamAppMeetrEventMapper.selectPageByQ(page, dto, currentBrandId);
    }

    @Override
    @Transactional
    public void newEvent(TeamAppMeetrNewEventsDTO dto,String thumbnailUri,String oasisId) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // create event
        AppMeetrEvent event = new AppMeetrEvent()
                .setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setCategory(dto.getCategory())
                .setThumbnail(thumbnailUri)
                .setHostedBrandId(currentBrandId)
                .setOasisId(oasisId)
                .setOasisChannelId(dto.getChannel())
                .setDescription(dto.getDescription())
                .setLocation(dto.getLocation())
                .setActivityStartAt(dto.getActivityStartAt())
                .setTopics(dto.getTopics())
                .setDuration(dto.getDuration())
                .setDurationType(dto.getDurationType())
                .setMaxSeats(dto.getMaxSeats())
                .setBudget(dto.getBudget())
                .setEventType(dto.getEventType())
                .setOnlineLink(dto.getOnlineLink())
                .setEventStatus(AppMeetrEventStatusEnum.PREPARING.getValue())
                .setAllowGuests(dto.getAllowGuests())
                .setAttendees(1)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppMeetrEventMapper.insert(event);

        // add creator as first attendee
        AppMeetrAttendee attendee = new AppMeetrAttendee()
                .setId(IdUtil.simpleUUID())
                .setEventId(event.getId())
                .setMemberBrandId(currentBrandId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamAppMeetrAttendeeMapper.insert(attendee);
    }

    @Override
    public TeamAppMeetrFetchOneEventInfoRO fetchEventInfo(String eventId) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        TeamAppMeetrFetchOneEventInfoRO info = teamAppMeetrEventMapper.selectOneEventById(eventId, currentBrandId);

        return info;
    }

    @Override
    @Transactional
    public void editEvent(TeamAppMeetrEditEventsDTO dto) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // query event
        AppMeetrEvent event = teamAppMeetrEventMapper.selectById(dto.getId());
        if (event == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        // only the creator (host) can edit
        if (!currentBrandId.equals(event.getHostedBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        // update fields
        event.setTitle(dto.getTitle())
                .setCategory(dto.getCategory().getCode())
                .setDescription(dto.getDescription())
                .setLocation(dto.getLocation())
                .setActivityStartAt(dto.getActivityStartAt())
                .setTopics(dto.getTopics())
                .setDuration(dto.getDuration())
                .setDurationType(dto.getDurationType().getValue())
                .setMaxSeats(dto.getMaxSeats())
                .setBudget(dto.getBudget())
                .setEventType(dto.getEventType().getValue())
                .setEventStatus(dto.getEventStatus().getValue())
                .setOnlineLink(dto.getOnlineLink())
                .setAllowGuests(dto.getAllowGuests())
                .setModifiedAt(new Date());


        teamAppMeetrEventMapper.updateById(event);
    }

    @Override
    @Transactional
    public void deleteEvent(String eventId) {

        // delete attendees first, then the event
        teamAppMeetrAttendeeMapper.delete(
                Wrappers.<AppMeetrAttendee>lambdaQuery()
                        .eq(AppMeetrAttendee::getEventId, eventId)
        );
        teamAppMeetrEventMapper.deleteById(eventId);
    }

    @Override
    public AppMeetrEvent fetchEventAllInfo(String eventId) {
        return teamAppMeetrEventMapper.selectById(eventId);
    }

    @Override
    @Transactional
    public void reserveEvent(String eventId) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // 1. query event
        AppMeetrEvent event = teamAppMeetrEventMapper.selectById(eventId);
        if (event == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        // 2. check event status - only active/preparing events can be reserved
        if (!AppMeetrEventStatusEnum.PREPARING.getValue().equals(event.getEventStatus())) {
            throw new QuickMessageException("活动已失效");
        }

        // 3. check if the activity has already started
        if (event.getActivityStartAt() != null && LocalDateTime.now().isAfter(event.getActivityStartAt())) {
            throw new QuickMessageException("已过预约时间");
        }

        // 4. check if already reserved
        Long count = teamAppMeetrAttendeeMapper.selectCount(
                Wrappers.<AppMeetrAttendee>lambdaQuery()
                        .eq(AppMeetrAttendee::getEventId, eventId)
                        .eq(AppMeetrAttendee::getMemberBrandId, currentBrandId)
        );
        if (count > 0) {
            throw new QuickMessageException("您已预约过该活动");
        }

        // 5. check seat availability ，0 means unlimited
        if (event.getAttendees() >= event.getMaxSeats() && event.getMaxSeats() != 0) {
            throw new QuickMessageException("活动席位已满额");
        }

        // 6. add attendee
        AppMeetrAttendee attendee = new AppMeetrAttendee()
                .setId(IdUtil.simpleUUID())
                .setEventId(eventId)
                .setMemberBrandId(currentBrandId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamAppMeetrAttendeeMapper.insert(attendee);

        // 7. increment attendees count
        teamAppMeetrEventMapper.update(null, Wrappers.<AppMeetrEvent>lambdaUpdate()
                .eq(AppMeetrEvent::getId, attendee.getEventId())
                .setSql("attendees = attendees + 1")
                .set(AppMeetrEvent::getModifiedAt, new Date())
        );
    }

    @Override
    @Transactional
    public void delOneAttendance(String attendanceId) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // find attendee record
        AppMeetrAttendee attendee = teamAppMeetrAttendeeMapper.selectById(attendanceId);
        if (attendee == null) {
            throw new QuickMessageException("预约记录不存在");
        }

        // only the attendee owner can cancel
        if (!currentBrandId.equals(attendee.getMemberBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }


        //  delete the attendee record
        teamAppMeetrAttendeeMapper.deleteById(attendanceId);

        //  find the associated event
        AppMeetrEvent event = teamAppMeetrEventMapper.selectById(attendee.getEventId());
        if (event == null) {
            throw new QuickMessageException("活动信息不存在");
        }

        teamAppMeetrEventMapper.update(null, Wrappers.<AppMeetrEvent>lambdaUpdate()
                .eq(AppMeetrEvent::getId, attendee.getEventId())
                // 保证在数据库层面 attendees 大于 0 时才扣减，防止并发时扣成负数
                .gt(AppMeetrEvent::getAttendees, 0)
                .setSql("attendees = attendees - 1")
                .set(AppMeetrEvent::getModifiedAt, new Date())
        );

    }

    @Override
    public IPage<TeamAppMeetrFetchEventAttendeesPageRO> fetchEventAttendees(TeamAppMeetrFetchEventAttendeesPageDTO dto) {
        IPage<TeamAppMeetrFetchEventAttendeesPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamAppMeetrAttendeeMapper.selectEventAttendeesPage(page, dto);
    }

    @Override
    public IPage<TeamAppMeetrFetchMemberAttendancesPageRO> fetchMemberAttendances(TeamAppMeetrFetchMemberAttendancesPageDTO dto) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        IPage<TeamAppMeetrFetchMemberAttendancesPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamAppMeetrAttendeeMapper.selectMemberAttendancesPage(page, dto, currentBrandId);
    }

    @Override
    @Transactional
    public void changeEventThumbnail(String eventId, String thumbnailUri) {
        teamAppMeetrEventMapper.update(null,
                Wrappers.<AppMeetrEvent>lambdaUpdate()
                        .set(AppMeetrEvent::getThumbnail, thumbnailUri)
                        .set(AppMeetrEvent::getModifiedAt, new Date())
                        .eq(AppMeetrEvent::getId, eventId)
        );
    }

    @Override
    public void changeChannelSetting(TeamAppMeetrChangeChannelSettingDTO dto) {
        AppMeetrChannelGuide guide = new AppMeetrChannelGuide();
        guide.setPostStrategy(dto.getPostStrategy());
        LambdaQueryWrapper<OasisChannel> channelWrapper = Wrappers.lambdaQuery();
        channelWrapper.eq(OasisChannel::getId,dto.getChannelId());

        OasisChannel channel = new OasisChannel();
        channel.setId(dto.getChannelId())
                .setChannelName(dto.getChannelName())
                .setChannelDesc(dto.getChannelDesc())
                .setGuide(gson.toJson(guide));
        teamOasisChannelMapper.update(channel, channelWrapper);
    }
}
