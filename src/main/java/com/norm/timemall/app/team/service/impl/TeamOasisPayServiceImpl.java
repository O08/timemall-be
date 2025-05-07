package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.TransDirectionEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.Transactions;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.domain.dto.TeamTopUpOasisDTO;
import com.norm.timemall.app.team.mapper.TeamFinAccountMapper;
import com.norm.timemall.app.team.mapper.TeamTransactionsMapper;
import com.norm.timemall.app.team.service.TeamOasisPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TeamOasisPayServiceImpl implements TeamOasisPayService {
    @Autowired
    private TeamFinAccountMapper teamFinAccountMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;
    @Transactional
    @Override
    public void topUptoOasis(TeamTopUpOasisDTO dto) {
        // query brand account
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();
        FinAccount brandFinAccount = teamFinAccountMapper.selectOneByFidForUpdate(brandId, FidTypeEnum.BRAND.getMark());
        // query Oasis account
        FinAccount oasisFinAccount = teamFinAccountMapper.selectOneByFidForUpdate(dto.getOasisId(),FidTypeEnum.OASIS.getMark());
        // validate
        if(brandFinAccount == null || oasisFinAccount == null ||
                brandFinAccount.getDrawable().compareTo(dto.getAmount())<0){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // insert trans
        String transNo = IdUtil.simpleUUID();
        Transactions creditTrans = new Transactions();
        creditTrans.setId(IdUtil.simpleUUID())
                .setFid(dto.getOasisId())
                .setFidType(FidTypeEnum.OASIS.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.TOPUP_OASIS.getMark())
                .setTransTypeDesc(TransTypeEnum.TOPUP_OASIS.getDesc())
                .setAmount(dto.getAmount())
                .setDirection(TransDirectionEnum.CREDIT.getMark())
                .setRemark(TransTypeEnum.TOPUP_OASIS.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        Transactions debitTrans = new Transactions();
        debitTrans.setId(IdUtil.simpleUUID())
                .setFid(brandId)
                .setFidType(FidTypeEnum.BRAND.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.TOPUP_OASIS.getMark())
                .setTransTypeDesc(TransTypeEnum.TOPUP_OASIS.getDesc())
                .setAmount(dto.getAmount())
                .setDirection(TransDirectionEnum.DEBIT.getMark())
                .setRemark(TransTypeEnum.TOPUP_OASIS.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamTransactionsMapper.insert(creditTrans);
        teamTransactionsMapper.insert(debitTrans);

        // action option
        BigDecimal brandBalance = brandFinAccount.getDrawable().subtract(dto.getAmount());
        brandFinAccount.setDrawable(brandBalance);
        teamFinAccountMapper.updateById(brandFinAccount);

        BigDecimal oasisBalance = oasisFinAccount.getDrawable().add(dto.getAmount());
        oasisFinAccount.setDrawable(oasisBalance);
        teamFinAccountMapper.updateById(oasisFinAccount);


    }
}
