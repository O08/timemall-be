package com.norm.timemall.app.team.service.impl;

import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.OasisPointBusinessTypeEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.service.BaseOasisPointsService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.team.domain.dto.TeamTopUpOasisDTO;
import com.norm.timemall.app.team.helper.TeamCommissionHelper;
import com.norm.timemall.app.team.mapper.TeamFinAccountMapper;
import com.norm.timemall.app.team.mapper.TeamTransactionsMapper;
import com.norm.timemall.app.team.service.TeamOasisPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TeamOasisPayServiceImpl implements TeamOasisPayService {
    @Autowired
    private TeamFinAccountMapper teamFinAccountMapper;
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;

    @Autowired
    private DefaultPayService defaultPayService;

    @Autowired
    private BaseOasisPointsService baseOasisPointsService;

    @Autowired
    private TeamCommissionHelper teamCommissionHelper;
    @Transactional
    @Override
    public void topUptoOasis(TeamTopUpOasisDTO dto) {
        // query brand account
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        TransferBO bo = defaultPayService.generateTransferBO(TransTypeEnum.TOPUP_OASIS.getMark(),
                FidTypeEnum.OASIS.getMark(),dto.getOasisId(),FidTypeEnum.BRAND.getMark(),brandId,dto.getAmount(),dto.getOasisId());

        String tradeNo = defaultPayService.transfer(new Gson().toJson(bo));

        // update or insert fin_distribute
        teamCommissionHelper.createFinDistributeIfNotExists(dto.getOasisId(), brandId);
        baseOasisPointsService.topUp(dto.getOasisId(),brandId,dto.getAmount(),"助力部落", OasisPointBusinessTypeEnum.TOP_UP.getMark(), tradeNo, "支付返回："+tradeNo);



    }
}
