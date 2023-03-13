package com.norm.timemall.app.team.service.impl;

import com.norm.timemall.app.team.domain.ro.TeamOasisMemberRO;
import com.norm.timemall.app.team.mapper.TeamOasisMemberMapper;
import com.norm.timemall.app.team.service.TeamOasisMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TeamOasisMemberServiceImpl implements TeamOasisMemberService {

    @Autowired
    private TeamOasisMemberMapper teamOasisMemberMapper;
    @Override
    public ArrayList<TeamOasisMemberRO> findOasisMember(String oasisId) {
        return teamOasisMemberMapper.selectListByOasisId(oasisId);
    }
}
