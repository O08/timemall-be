package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.mo.OasisInvitationLink;
import com.norm.timemall.app.team.domain.dto.TeamCreateOasisInvitationLinkDTO;
import com.norm.timemall.app.team.domain.dto.TeamQueryInvitationLinkInfoDTO;
import com.norm.timemall.app.team.domain.dto.TeamQueryInvitationLinkPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamQueryInvitationLinkInfoRO;
import com.norm.timemall.app.team.domain.ro.TeamQueryInvitationLinkPageRO;
import com.norm.timemall.app.team.domain.vo.TeamQueryInvitationLinkInfoVO;
import com.norm.timemall.app.team.domain.vo.TeamQueryInvitationLinkPageVO;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamOasisInvitationLinkService;
import com.norm.timemall.app.team.service.TeamOasisJoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class TeamSettingOasisInvitationLinkController {
    @Autowired
    private TeamOasisInvitationLinkService teamOasisInvitationLinkService;
    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private TeamOasisJoinService teamOasisJoinService;

    @GetMapping("/api/v1/oasis/setting/invitation_link/query")
    public TeamQueryInvitationLinkPageVO queryInvitationLink(@Validated TeamQueryInvitationLinkPageDTO dto) {
        // validate role must be admin
        boolean isCreator = teamDataPolicyService.passIfBrandIsCreatorOfOasis(dto.getOasisId());
        if (!isCreator) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        IPage<TeamQueryInvitationLinkPageRO> links= teamOasisInvitationLinkService.queryInvitationLink(dto);

        TeamQueryInvitationLinkPageVO vo = new TeamQueryInvitationLinkPageVO();
        vo.setLink(links);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/open/oasis/setting/invitation_link/info")
    public TeamQueryInvitationLinkInfoVO queryInvitationLinkInfo(@Validated TeamQueryInvitationLinkInfoDTO dto){
        TeamQueryInvitationLinkInfoVO vo =new TeamQueryInvitationLinkInfoVO();
        TeamQueryInvitationLinkInfoRO link = teamOasisInvitationLinkService.queryInvitationLinkInfo(dto);
        vo.setLink(link);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @PostMapping("/api/v1/oasis/setting/invitation_link/create")
    public SuccessVO createInvitationLink(@Validated @RequestBody TeamCreateOasisInvitationLinkDTO dto) {
        // validate role must be admin
        boolean isCreator = teamDataPolicyService.passIfBrandIsCreatorOfOasis(dto.getOasisId());
        if (!isCreator) {
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        teamOasisInvitationLinkService.createInvitationLink(dto);

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @DeleteMapping("/api/v1/oasis/setting/invitation_link/{id}/del")
    public SuccessVO delInvitationLink(@PathVariable String id) {
        OasisInvitationLink link = teamOasisInvitationLinkService.findOneInvitationLink(id);
        if(link == null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        if(!teamDataPolicyService.passIfBrandIsCreatorOfOasis(link.getOasisId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
        teamOasisInvitationLinkService.delInvitationLink(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/oasis/invitation_link/{id}/be_member")
    public SuccessVO beMemberOfOasis(@PathVariable String id) {
        OasisInvitationLink link = teamOasisInvitationLinkService.findOneInvitationLink(id);
        if(link == null){
            throw new ErrorCodeException(CodeEnum.NOT_FOUND_DATA);
        }
        // if link expire, return
        if(link.getExpireTime().before(new Date())){
            throw new QuickMessageException("链接已过期");
        }

        // if link no quota, return, max uses is zero means unlimited
        boolean isLimited = link.getMaxUses() > 0;
        if(isLimited && link.getUsageCount() >= link.getMaxUses()){
            throw new QuickMessageException("链接可用次数已用完");
        }

        teamOasisJoinService.followOasisUseInvitationLink(link.getOasisId(), link.getGrantedOasisRoleId());
        
        // Increment usage count after successful use of invitation link
        teamOasisInvitationLinkService.autoIncrementUsageCount(id);

        return new SuccessVO(CodeEnum.SUCCESS);
    }







}
