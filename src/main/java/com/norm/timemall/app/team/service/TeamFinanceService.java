package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.pojo.TeamFinDistriution;
import com.norm.timemall.app.team.domain.ro.TeamFinBoardRO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface TeamFinanceService {


    TeamFinBoardRO kanban();

    TeamFinDistriution findFinDistribution();


    TeamFinBoardRO oasisKanban(String oasisId);

    BigDecimal findPointInOasis(String oasisId, String brandId);

    BigDecimal findPointInOasisUseChannel(String channel);
}
