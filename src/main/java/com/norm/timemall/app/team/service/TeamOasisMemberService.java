package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OasisMember;
import com.norm.timemall.app.team.domain.dto.TeamFetchOasisMemberPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamInviteToOasisDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchOasisMemberPageRO;
import com.norm.timemall.app.team.domain.ro.TeamOasisMemberRO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public interface TeamOasisMemberService {
    ArrayList<TeamOasisMemberRO> findOasisMember(String oasisId,String q);

    void unfollowOasis(String oasisId,String brandId);

    IPage<TeamFetchOasisMemberPageRO> findOasisMemberAndRole(TeamFetchOasisMemberPageDTO dto);

    OasisMember findOneMember(String oasisId,String memberBrandId);
}
