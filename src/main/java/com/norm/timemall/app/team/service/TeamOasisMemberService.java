package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.TeamInviteToOasisDTO;
import com.norm.timemall.app.team.domain.ro.TeamOasisMemberRO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface TeamOasisMemberService {
    ArrayList<TeamOasisMemberRO> findOasisMember(String oasisId);

}
