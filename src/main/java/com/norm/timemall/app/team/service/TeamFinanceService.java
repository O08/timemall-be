package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.team.domain.dto.TeamFinDistributionDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFinDistriutionItem;
import com.norm.timemall.app.team.domain.ro.TeamFinBoardRO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface TeamFinanceService {


    TeamFinBoardRO kanban();

    IPage<TeamFinDistriutionItem> findFinDistribution(TeamFinDistributionDTO dto);


    TeamFinBoardRO oasisKanban(String oasisId);

    BigDecimal findPointInOasis(String oasisId, String brandId);

    BigDecimal findPointInOasisUseChannel(String channel);
}
