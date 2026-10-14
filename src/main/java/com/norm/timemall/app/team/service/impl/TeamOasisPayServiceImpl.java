package com.norm.timemall.app.team.service.impl;

import com.google.gson.Gson;
import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Brand;
import com.norm.timemall.app.base.mo.UserSponsorshipRecord;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.service.BaseOasisPointsService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.team.domain.dto.TeamSponsorBrandDTO;
import com.norm.timemall.app.team.domain.dto.TeamTopUpOasisDTO;
import com.norm.timemall.app.team.helper.TeamCommissionHelper;
import com.norm.timemall.app.team.mapper.TeamBrandMapper;
import com.norm.timemall.app.team.mapper.TeamFinAccountMapper;
import com.norm.timemall.app.team.mapper.TeamTransactionsMapper;
import com.norm.timemall.app.team.mapper.TeamUserSponsorshipRecordMapper;
import com.norm.timemall.app.team.service.TeamOasisPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
public class TeamOasisPayServiceImpl implements TeamOasisPayService {
    @Autowired
    private TeamFinAccountMapper teamFinAccountMapper;
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;
    @Autowired
    private TeamBrandMapper teamBrandMapper;

    @Autowired
    private TeamUserSponsorshipRecordMapper teamUserSponsorshipRecordMapper;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void sponsorBrand(TeamSponsorBrandDTO dto) {
        // sponsor brand account
        String sponsorBrandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // 被赞助品牌必须存在且未废弃
        Brand brand = teamBrandMapper.selectById(dto.getSponsoreeBrandId());
        if (brand == null) {
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_NOT_EXIST);
        }
        if (BrandMarkEnum.CLOSED.getMark().equals(brand.getMark())) {
            throw new ErrorCodeException(CodeEnum.USER_ACCOUNT_LOCKED);
        }
        // 不允许自己赞助自己
        if (dto.getSponsoreeBrandId().equals(sponsorBrandId)) {
            throw new ErrorCodeException(CodeEnum.FALSE_SHOPPING);
        }

        // 赞助记录（主键同时作为资金流水的业务单号）
        UserSponsorshipRecord record = new UserSponsorshipRecord()
                .setId(IdUtil.simpleUUID())
                .setSponsorBrandId(sponsorBrandId)
                .setSponsoreeBrandId(dto.getSponsoreeBrandId())
                .setAmount(dto.getAmount())
                .setCurrency(Currency.CNY.getCode())
                .setSponsorMessage(dto.getMessage())
                .setCreatedAt(new Date())
                .setModifiedAt(new Date());

        // 赞助资金流向：当前品牌 -> 被赞助品牌
        TransferBO bo = defaultPayService.generateTransferBO(TransTypeEnum.SPONSOR_BRAND.getMark(),
                FidTypeEnum.BRAND.getMark(), dto.getSponsoreeBrandId(), FidTypeEnum.BRAND.getMark(), sponsorBrandId,
                dto.getAmount(), record.getId());

        defaultPayService.transfer(new Gson().toJson(bo));

        // 资金划转成功后落赞助记录，与流水同事务
        teamUserSponsorshipRecordMapper.insert(record);
    }
}
