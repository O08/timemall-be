package com.norm.timemall.app.team.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.FinDistribute;
import com.norm.timemall.app.team.domain.dto.TeamFinDistributionDTO;
import com.norm.timemall.app.team.domain.pojo.TeamFinDistriutionItem;
import com.norm.timemall.app.team.domain.ro.TeamFinBoardRO;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TeamFinanceServiceImpl implements TeamFinanceService {

    @Autowired
    private TeamFinAccountMapper teamFinAccountMapper;
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;

    @Autowired
    private TeamFinDistributeMapper teamFinDistributeMapper;


    @Override
    public TeamFinBoardRO kanban() {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        FinAccount account = teamFinAccountMapper.selectOneByFid(brandId, FidTypeEnum.BRAND.getMark());
        TeamFinBoardRO ro = new TeamFinBoardRO();
        ro.setAmount(account==null ? BigDecimal.ZERO : account.getAmount());
        ro.setDrawable(account==null? BigDecimal.ZERO :account.getDrawable());
        return ro;
    }

    @Override
    public IPage<TeamFinDistriutionItem> findFinDistribution(TeamFinDistributionDTO dto) {
        Page<TeamFinDistriutionItem> page = new Page<>();
        page.setCurrent(dto.getCurrent());
        page.setSize(dto.getSize());
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return teamFinDistributeMapper.selectDistributeByBrandId(page,dto,brandId,
                FidTypeEnum.BRAND.getMark(),FidTypeEnum.OASIS.getMark());

    }

    @Override
    public TeamFinBoardRO oasisKanban(String oasisId) {
        FinAccount account = teamFinAccountMapper.selectOneByFid(oasisId, FidTypeEnum.OASIS.getMark());
        TeamFinBoardRO ro = new TeamFinBoardRO();
        ro.setAmount(account==null ? BigDecimal.ZERO : account.getAmount());
        ro.setDrawable(account==null? BigDecimal.ZERO :account.getDrawable());
        return ro;
    }

    @Override
    public BigDecimal findPointInOasis(String oasisId, String brandId) {

        FinDistribute finDistribute = teamFinDistributeMapper.selectDistributeByBrandIdAndOasisId(brandId, oasisId);
        return finDistribute == null ? BigDecimal.ZERO : finDistribute.getAmount() ;
    }

    @Override
    public BigDecimal findPointInOasisUseChannel(String channel) {
        String currentUserBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        FinDistribute finDistribute = teamFinDistributeMapper.selectDistributeByChannel(channel,currentUserBrandId);
        return finDistribute == null ? BigDecimal.ZERO : finDistribute.getAmount() ;
    }
}
