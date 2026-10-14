package com.norm.timemall.app.team.service.impl;

import com.norm.timemall.app.team.mapper.TeamOasisIndMapper;
import com.norm.timemall.app.team.service.TeamOasisIndService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamOasisIndServiceImpl implements TeamOasisIndService {
    @Autowired
    private TeamOasisIndMapper teamOasisIndMapper;
    @Override
    public void calOasisIndex(String oasisId) {
        teamOasisIndMapper.calOasisIndexById(oasisId);
    }
}
