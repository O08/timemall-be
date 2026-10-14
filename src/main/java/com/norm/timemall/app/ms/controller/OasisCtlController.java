package com.norm.timemall.app.ms.controller;


import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;

import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.service.FileStoreService;

import com.norm.timemall.app.ms.service.MsGroupMemberRelService;
import com.norm.timemall.app.ms.service.MsGroupService;
import com.norm.timemall.app.team.domain.ro.FetchOneOasisChannelGeneralInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamOasisFetchUserCtaInfoRO;
import com.norm.timemall.app.team.domain.vo.TeamOasisFetchUserCtaInfoVO;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamOasisChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OasisCtlController {

    @Autowired
    private MsGroupMemberRelService msGroupMemberRelService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;
    @Autowired
    private MsGroupService msGroupService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private TeamOasisChannelService teamOasisChannelService;


    @PutMapping("/api/v1/app/oasis/{channel}/user/{id}/unmute")
    public SuccessVO unmuteUser(@PathVariable("channel") String channel, @PathVariable("id") String userId) {
        boolean isAdmin = teamDataPolicyService.validateChannelAdminRoleUseChannelId(channel);
        boolean beAdminUserId= SecurityUserHelper.getCurrentPrincipal().getUserId().equals(userId);

        if(!isAdmin || beAdminUserId){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        FetchOneOasisChannelGeneralInfoRO channelGeneralInfo = teamOasisChannelService.findOasisOneChannelGeneralInfo(channel);
        if(channelGeneralInfo==null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        msGroupMemberRelService.unbanOneUser(channelGeneralInfo.getOasisId(), userId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/app/oasis/{oasis_id}/user/{user_id}/cta_info")
    public TeamOasisFetchUserCtaInfoVO fetchUserCtaInfo(@PathVariable("oasis_id") String oasisId, @PathVariable("user_id") String userId) {

        TeamOasisFetchUserCtaInfoRO ctaInfoRO = msGroupMemberRelService.findUserCtaInfo(oasisId, userId);

        TeamOasisFetchUserCtaInfoVO vo =new TeamOasisFetchUserCtaInfoVO();
        vo.setResponseCode(CodeEnum.SUCCESS);
        vo.setCta(ctaInfoRO);
        return vo;

    }

    @PutMapping("/api/v1/app/oasis/{channel}/user/{id}/mute")
    public SuccessVO muteUser(@PathVariable("channel") String channel, @PathVariable("id") String userId) {

        boolean isAdmin = teamDataPolicyService.validateChannelAdminRoleUseChannelId(channel);
        boolean beAdminUserId= SecurityUserHelper.getCurrentPrincipal().getUserId().equals(userId);

        if(!isAdmin || beAdminUserId){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }

        FetchOneOasisChannelGeneralInfoRO channelGeneralInfo = teamOasisChannelService.findOasisOneChannelGeneralInfo(channel);
        if(channelGeneralInfo==null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }


        msGroupMemberRelService.banOneUser(channelGeneralInfo.getOasisId(), userId);
        return new SuccessVO(CodeEnum.SUCCESS);
    }


}
