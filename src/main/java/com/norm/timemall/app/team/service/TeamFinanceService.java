package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.pojo.TeamFinDistriution;
import com.norm.timemall.app.team.domain.ro.TeamFinBoardRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamFinanceService {
    void orderObj(String objId);

    TeamFinBoardRO kanban();

    TeamFinDistriution findFinDistribution();


}
