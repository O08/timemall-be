package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.TeamFetchFriendListDTO;
import com.norm.timemall.app.team.domain.dto.TeamInviteToOasisDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFetchFriendList;
import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
import com.norm.timemall.app.team.domain.ro.TeamJoinedRO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface TeamOasisJoinService {
    ArrayList<TeamInviteRO> findInvitedOasis(String brandId);

    void acceptOasisInvitation(String oasisId);

    void inviteBrand(TeamInviteToOasisDTO dto);

    ArrayList<TeamJoinedRO> findJoinedOasis(String brandId);

    void followOasis(String oasisId,String privateCode);

    void unfollowOasis(String oasisId,String brandId);

    void removeOasisInvitation(String id);

    TeamFetchFriendList findFriendThatNotInOasis(TeamFetchFriendListDTO dto);

    ArrayList<TeamJoinedRO> findShareOasis(String brandId);

    void followOasisUseInvitationLink(String oasisId,String roleId);
}
