package com.norm.timemall.app.team.service;

import com.norm.timemall.app.base.mo.OasisChannel;
import com.norm.timemall.app.base.mo.OasisRole;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.vo.TeamFetchOasisMemberRoleConfigurationVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchOasisRolesVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchRoleChannelVO;
import com.norm.timemall.app.team.domain.vo.TeamGetChannelOwnedByRoleVO;
import org.springframework.stereotype.Service;

@Service
public interface TeamOasisRoleService {
    TeamFetchOasisRolesVO findOasisRoles(TeamFetchOasisRolesDTO dto);

    void newOasisRole(TeamCreateOasisRoleDTO dto);

    OasisRole findOneRole(String roleId);

    void changeRole(TeamEditOasisRoleDTO dto);

    void delRole(String id);

    TeamFetchRoleChannelVO findRoleChannel(TeamFetchRoleChannelDTO dto);

    void toggleChannelPermission(TeamOasisRoleConfigChannelDTO dto);

    OasisChannel finOneChannel(String oasisChannelId);

    TeamGetChannelOwnedByRoleVO findChannelOwnedByRole(String id);

    TeamFetchOasisMemberRoleConfigurationVO findOasisMemberRoleConfiguration(TeamFetchOasisMemberRoleConfigurationDTO dto);

    void toggleMemberRole(String oasisId,TeamOasisMemberRoleConfigDTO dto);
}
