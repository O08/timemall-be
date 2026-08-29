package com.norm.timemall.app.team.controller;

import cn.hutool.core.io.FileTypeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AppMeetrEvent;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.ms.constant.ChatSupportUploadImageFormat;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.pojo.AppMeetrChannelGuide;
import com.norm.timemall.app.team.domain.ro.FetchOneOasisChannelGeneralInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrDiscoveryEventsPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchEventAttendeesPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchMemberAttendancesPageRO;
import com.norm.timemall.app.team.domain.ro.TeamAppMeetrFetchOneEventInfoRO;
import com.norm.timemall.app.team.domain.vo.TeamAppMeetrDiscoveryEventsPageVO;
import com.norm.timemall.app.team.domain.vo.TeamAppMeetrFetchMemberAttendancesPageVO;
import com.norm.timemall.app.team.domain.vo.TeamAppMeetrFetchEventAttendeesPageVO;
import com.norm.timemall.app.team.domain.vo.TeamAppMeetrFetchOneEventInfoVO;
import com.norm.timemall.app.team.service.TeamAppMeetrService;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamOasisChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;

@RestController
public class TeamAppMeetrController {
    @Autowired
    private TeamAppMeetrService teamAppMeetrService;

    @Autowired
    private TeamOasisChannelService teamOasisChannelService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private FileStoreService fileStoreService;

    @GetMapping(value = "/api/v1/app/meetr/events/find")
    public TeamAppMeetrDiscoveryEventsPageVO discoveryEvents(@Validated TeamAppMeetrDiscoveryEventsPageDTO dto) {
        IPage<TeamAppMeetrDiscoveryEventsPageRO> event = teamAppMeetrService.discoveryEvents(dto);
        TeamAppMeetrDiscoveryEventsPageVO vo = new TeamAppMeetrDiscoveryEventsPageVO();
        vo.setEvent(event);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @PostMapping("/api/v1/app/meetr/event/new")
    public SuccessVO newEvent(@Validated TeamAppMeetrNewEventsDTO dto) throws IOException {

        // validate file
        if(dto.getThumbnail() == null || dto.getThumbnail().isEmpty()){
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        String fileType= FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e->e.equals(fileType));
        if(notInExtensions){
            throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }

        FetchOneOasisChannelGeneralInfoRO channelGeneralInfo = teamOasisChannelService.findOasisOneChannelGeneralInfo(dto.getChannel());
        if(channelGeneralInfo == null) throw new QuickMessageException("Channel not found");
        AppMeetrChannelGuide  channelGuide =  new Gson().fromJson(channelGeneralInfo.getGuide(), AppMeetrChannelGuide.class);
        boolean isMemberCanPost = channelGuide != null
                && SwitchCheckEnum.ENABLE.getMark().equals(channelGuide.getPostStrategy());

        boolean canCreate = isMemberCanPost ? teamDataPolicyService.alreadyOasisMember(dto.getChannel()) : teamDataPolicyService.validateChannelAdminRoleUseChannelId(dto.getChannel());
        if (!canCreate) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        String thumbnailUri = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(
                dto.getThumbnail(), FileStoreDir.MEETR_EVENT_THUMBNAIL);

        teamAppMeetrService.newEvent(dto,thumbnailUri,channelGeneralInfo.getOasisId());
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/app/meetr/event/{id}/info")
    public TeamAppMeetrFetchOneEventInfoVO fetchEventInfo(@PathVariable("id") String eventId) {
        TeamAppMeetrFetchOneEventInfoRO info = teamAppMeetrService.fetchEventInfo(eventId);

        TeamAppMeetrFetchOneEventInfoVO vo = new TeamAppMeetrFetchOneEventInfoVO();
        vo.setInfo(info);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @PutMapping("/api/v1/app/meetr/event/edit")
    public SuccessVO editEvent(@Validated @RequestBody TeamAppMeetrEditEventsDTO dto) {
        teamAppMeetrService.editEvent(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @DeleteMapping("/api/v1/app/meetr/event/{id}/del")
    public SuccessVO deleteEvent(@PathVariable("id") String eventId) {
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // query event
        AppMeetrEvent event = teamAppMeetrService.fetchEventAllInfo(eventId);
        if (event == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }

        // only the author (hostedBrandId) or channel admin can delete
        boolean isAuthor = currentBrandId.equals(event.getHostedBrandId());
        boolean isAdmin = teamDataPolicyService.validateChannelAdminRoleUseChannelId(event.getOasisChannelId());
        if (!isAuthor && !isAdmin) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        teamAppMeetrService.deleteEvent(eventId);

        // delete old thumbnail file
        if (event.getThumbnail() != null && !event.getThumbnail().isBlank()) {
            fileStoreService.deleteImageAndAvifFile(event.getThumbnail());
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping("/api/v1/app/meetr/event/{id}/reserve")
    public SuccessVO reserveEvent(@PathVariable("id") String eventId) {
        teamAppMeetrService.reserveEvent(eventId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/app/meetr/event/thumbnail/change")
    public SuccessVO changeThumbnail(@Validated TeamAppMeetrChangeThumbnailDTO dto) throws IOException {

        // validate file
        if (dto.getThumbnail() == null || dto.getThumbnail().isEmpty()) {
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        String fileType = FileTypeUtil.getType(dto.getThumbnail().getInputStream());
        boolean notInExtensions = Arrays.stream(ChatSupportUploadImageFormat.extensions).noneMatch(e -> e.equals(fileType));
        if (notInExtensions) {
            throw new ErrorCodeException(CodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }

        // query event: check existence + ownership + get old thumbnail
        AppMeetrEvent event = teamAppMeetrService.fetchEventAllInfo(dto.getEventId());
        if (event == null) {
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        String currentBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        if (!currentBrandId.equals(event.getHostedBrandId())) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        String oldThumbnail = event.getThumbnail();

        // store new thumbnail
        String thumbnailUri = fileStoreService.storeImageAndProcessAsAvifWithUnlimitedAccess(
                dto.getThumbnail(), FileStoreDir.MEETR_EVENT_THUMBNAIL);

        // update event thumbnail
        teamAppMeetrService.changeEventThumbnail(dto.getEventId(), thumbnailUri);

        // delete old thumbnail file
        if (oldThumbnail != null && !oldThumbnail.isBlank()) {
            fileStoreService.deleteImageAndAvifFile(oldThumbnail);
        }

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/app/meetr/channel/setting")
    public SuccessVO changeChannelSetting(@Validated @RequestBody TeamAppMeetrChangeChannelSettingDTO dto) {
        // only admin can execute setting operation
        boolean validated = teamDataPolicyService.validateChannelAdminRoleUseChannelId(dto.getChannelId());
        if(!validated){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        teamAppMeetrService.changeChannelSetting(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @DeleteMapping("/api/v1/app/meetr/attendance/{id}/cancel")
    public SuccessVO delAttendance(@PathVariable("id") String attendanceId) {
        teamAppMeetrService.delOneAttendance(attendanceId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/app/meetr/event/attendees")
    public TeamAppMeetrFetchEventAttendeesPageVO getEventAttendees(@Validated TeamAppMeetrFetchEventAttendeesPageDTO dto) {
        IPage<TeamAppMeetrFetchEventAttendeesPageRO> attendee = teamAppMeetrService.fetchEventAttendees(dto);
        TeamAppMeetrFetchEventAttendeesPageVO vo = new TeamAppMeetrFetchEventAttendeesPageVO();
        vo.setAttendee(attendee);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/v1/app/meetr/member/attendances")
    public TeamAppMeetrFetchMemberAttendancesPageVO getMemberAttendances(@Validated TeamAppMeetrFetchMemberAttendancesPageDTO dto) {
        IPage<TeamAppMeetrFetchMemberAttendancesPageRO> attendance = teamAppMeetrService.fetchMemberAttendances(dto);
        TeamAppMeetrFetchMemberAttendancesPageVO vo = new TeamAppMeetrFetchMemberAttendancesPageVO();
        vo.setAttendance(attendance);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }


}
