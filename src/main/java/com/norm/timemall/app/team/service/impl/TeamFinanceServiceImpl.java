package com.norm.timemall.app.team.service.impl;


import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.FinDistribute;
import com.norm.timemall.app.team.domain.pojo.TeamFinDistriution;
import com.norm.timemall.app.team.domain.pojo.TeamFinDistriutionItem;
import com.norm.timemall.app.team.domain.ro.TeamFinBoardRO;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;

@Service
public class TeamFinanceServiceImpl implements TeamFinanceService {

    @Autowired
    private TeamAccountMapper teamAccountMapper;
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;

    @Autowired
    private TeamFinDistributeMapper teamFinDistributeMapper;


    @Override
    public TeamFinBoardRO kanban() {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        FinAccount account = teamAccountMapper.selectOneByFid(brandId, FidTypeEnum.BRAND.getMark());
        TeamFinBoardRO ro = new TeamFinBoardRO();
        ro.setAmount(account==null ? BigDecimal.ZERO : account.getAmount());
        ro.setDrawable(account==null? BigDecimal.ZERO :account.getDrawable());
        return ro;
    }

    @Override
    public TeamFinDistriution findFinDistribution() {
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        ArrayList<TeamFinDistriutionItem> items = teamFinDistributeMapper.selectDistributeByBrandId(brandId,
                FidTypeEnum.BRAND.getMark(),FidTypeEnum.OASIS.getMark());
        TeamFinDistriution ro = new TeamFinDistriution();
        ro.setRecords(items);
        return ro;
    }

    @Override
    public TeamFinBoardRO oasisKanban(String oasisId) {
        FinAccount account = teamAccountMapper.selectOneByFid(oasisId, FidTypeEnum.OASIS.getMark());
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
}
