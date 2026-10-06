package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.OasisRoleCoreEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.base.mo.OasisMember;
import com.norm.timemall.app.base.mo.OasisRole;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.vo.TeamFetchOasisMemberRoleConfigurationVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchOasisRolesVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchRoleChannelVO;
import com.norm.timemall.app.team.domain.vo.TeamGetChannelOwnedByRoleVO;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamOasisMemberService;
import com.norm.timemall.app.team.service.TeamOasisRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamSettingOasisRoleController {

    @Autowired
    private TeamOasisRoleService teamOasisRoleService;
    @Autowired
    private TeamDataPolicyService teamDataPolicyService;
    @Autowired
    private TeamOasisMemberService teamOasisMemberService;

    @GetMapping("/api/v1/team/oasis/role")
    public TeamFetchOasisRolesVO fetchOasisRoles(@Validated TeamFetchOasisRolesDTO dto){
       return teamOasisRoleService.findOasisRoles(dto);
    }
    @PostMapping("/api/v1/team/oasis/role/new")
    public SuccessVO createOasisRole(@Validated @RequestBody TeamCreateOasisRoleDTO dto){
        // validate user
        // check oasis ,if user is oasis creator ,pass
        boolean passed = teamDataPolicyService.passIfBrandIsCreatorOfOasis(dto.getOasisId());

        if(!passed){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamOasisRoleService.newOasisRole(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/team/oasis/role/change")
    public SuccessVO editOasisRole(@Validated @RequestBody TeamEditOasisRoleDTO dto){
        OasisRole role = teamOasisRoleService.findOneRole(dto.getRoleId());
        if(role==null){
            throw new QuickMessageException("role not exist");
        }
        boolean passed = teamDataPolicyService.passIfBrandIsCreatorOfOasis(role.getOasisId());

        if(!passed){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamOasisRoleService.changeRole(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @DeleteMapping("/api/v1/team/oasis/role/{id}/del")
    public SuccessVO deleteOneRole(@PathVariable("id") String id){
        OasisRole role = teamOasisRoleService.findOneRole(id);
        if(role==null){
            throw new QuickMessageException("role not exist");
        }
        boolean passed = teamDataPolicyService.passIfBrandIsCreatorOfOasis(role.getOasisId());

        if(!passed){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamOasisRoleService.delRole(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @GetMapping("/api/v1/team/oasis/role/channel/query")
    public TeamFetchRoleChannelVO fetchRoleChannel(@Validated TeamFetchRoleChannelDTO dto){
        return  teamOasisRoleService.findRoleChannel(dto);
    }
    @PostMapping("/api/v1/team/oasis/role/channel/config")
    public SuccessVO configChannel(@Validated @RequestBody TeamOasisRoleConfigChannelDTO dto){
        OasisRole role = teamOasisRoleService.findOneRole(dto.getRoleId());
        if(role==null){
            throw new QuickMessageException("role not exist");
        }
        boolean passed = teamDataPolicyService.passIfBrandIsCreatorOfOasis(role.getOasisId());

        if(!passed){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // check channel
        OasisChannel channel =teamOasisRoleService.finOneChannel(dto.getOasisChannelId());
        if(channel==null){
            throw new QuickMessageException("channel not exist");
        }
        if(!channel.getOasisId().equals(role.getOasisId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        teamOasisRoleService.toggleChannelPermission(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @GetMapping("/api/v1/team/oasis/role/{id}/channel/query")
    public TeamGetChannelOwnedByRoleVO fetchChannelOwnedByRole(@PathVariable("id") String id){
       return  teamOasisRoleService.findChannelOwnedByRole(id);
    }
    @GetMapping("/api/v1/team/oasis/member/role/configuration/query")
    public TeamFetchOasisMemberRoleConfigurationVO fetchOasisMemberRoleConfiguration(@Validated TeamFetchOasisMemberRoleConfigurationDTO dto){
       return  teamOasisRoleService.findOasisMemberRoleConfiguration(dto);
    }
    @PostMapping("/api/v1/team/oasis/member/role/config")
    public SuccessVO TeamOasisMemberRoleConfig(@Validated @RequestBody TeamOasisMemberRoleConfigDTO dto){

        OasisRole role = teamOasisRoleService.findOneRole(dto.getRoleId());
        if(role==null){
            throw new QuickMessageException("role not exist");
        }
        if(OasisRoleCoreEnum.ADMIN.getMark().equals(role.getRoleCode())){
            throw new QuickMessageException("管理员身份组暂不支持授权");
        }
        boolean passed = teamDataPolicyService.passIfBrandIsCreatorOfOasis(role.getOasisId());

        if(!passed){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // check member
        OasisMember member= teamOasisMemberService.findOneMember(role.getOasisId(),dto.getMemberBrandId());
        if(member==null){
            throw new QuickMessageException("member not exist in oasis");
        }

        teamOasisRoleService.toggleMemberRole(role.getOasisId(),dto);

        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
