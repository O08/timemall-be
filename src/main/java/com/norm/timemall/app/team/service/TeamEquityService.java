package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OasisEquityOrder;
import com.norm.timemall.app.base.mo.OasisEquityPeriod;
import com.norm.timemall.app.team.domain.dto.TeamFetchEquityOrderPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamFetchEquityPeriodPageDTO;
import com.norm.timemall.app.team.domain.dto.TeamRegisterNewPeriodEquityDTO;
import com.norm.timemall.app.team.domain.dto.TeamBuyEquityDTO;
import com.norm.timemall.app.team.domain.dto.TeamFetchEquitySponsorOrderPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquityOrderPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquitySponsorOrderPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchEquityPeriodPageRO;
import com.norm.timemall.app.team.domain.vo.TeamFetchLatestPeriodEquitySummaryVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchEquitySponsorshipInfoVO;
import org.springframework.stereotype.Service;

@Service
public interface TeamEquityService {
    IPage<TeamFetchEquityPeriodPageRO> fetchEquityPeriods(TeamFetchEquityPeriodPageDTO dto);

    IPage<TeamFetchEquityOrderPageRO> fetchEquityOrders(TeamFetchEquityOrderPageDTO dto);

    TeamFetchLatestPeriodEquitySummaryVO fetchLatestPeriodEquitySummary(String oasisId);

    TeamFetchEquitySponsorshipInfoVO fetchEquitySponsorshipInfo(String oasisId);

    void registerNewPeriodEquity(TeamRegisterNewPeriodEquityDTO dto);

    void redeemEquity(String orderId);

    void buyEquity(TeamBuyEquityDTO dto);

    void writeOffEquity(String orderId);

    IPage<TeamFetchEquitySponsorOrderPageRO> fetchEquityOrdersForSponsor(TeamFetchEquitySponsorOrderPageDTO dto);

    OasisEquityPeriod getEquityPeriodById(String equityPeriodId);

    OasisEquityOrder getEquityOrderById(String orderId);
}
