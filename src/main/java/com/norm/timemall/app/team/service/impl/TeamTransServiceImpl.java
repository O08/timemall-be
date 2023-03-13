package com.norm.timemall.app.team.service.impl;

import com.norm.timemall.app.team.domain.pojo.TeamTrans;
import com.norm.timemall.app.team.mapper.TeamTransactionsMapper;
import com.norm.timemall.app.team.service.TeamTransService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamTransServiceImpl implements TeamTransService {
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;
    @Override
    public TeamTrans findTrans(String fid,String year,String month) {
        return teamTransactionsMapper.selectTransByFid(fid,year,month);
    }
}
