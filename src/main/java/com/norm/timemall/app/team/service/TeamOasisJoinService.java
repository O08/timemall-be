package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.TeamInviteToOasisDTO;
import com.norm.timemall.app.team.domain.ro.TeamInviteRO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface TeamOasisJoinService {
    ArrayList<TeamInviteRO> findInvitedOasis();

    void acceptOasisInvitation(String oasisId);

    void inviteBrand(TeamInviteToOasisDTO dto);
}
