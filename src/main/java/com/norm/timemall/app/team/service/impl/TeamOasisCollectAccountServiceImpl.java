package com.norm.timemall.app.team.service.impl;

import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.FinDistribute;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.mo.Transactions;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.service.BaseOasisPointsService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.team.domain.dto.TeamOasisAdminWithdrawDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisCollectAccountDTO;
import com.norm.timemall.app.team.mapper.TeamFinAccountMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import com.norm.timemall.app.team.mapper.TeamTransactionsMapper;
import com.norm.timemall.app.team.service.TeamOasisCollectAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TeamOasisCollectAccountServiceImpl implements TeamOasisCollectAccountService {
    @Autowired
    private TeamFinAccountMapper teamFinAccountMapper;
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;


    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private BaseOasisPointsService baseOasisPointsService;

    @Autowired
    private DefaultPayService defaultPayService;

    @Transactional
    @Override
    public void collectAccountFromOasis(TeamOasisCollectAccountDTO dto)
    {
        // query oasis
        Oasis oasis = teamOasisMapper.selectById(dto.getOasisId());

        if(oasis==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(OasisMarkEnum.BLOCKED.getMark().equals(oasis.getMark())){
            throw new ErrorCodeException(CodeEnum.OASIS_LOCKED);
        }

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        FinDistribute finDistribute= baseOasisPointsService.findOasisPointsInfo(dto.getOasisId(),brandId);


        // query Oasis account
        FinAccount oasisFinAccount = teamFinAccountMapper.selectOneByFid(dto.getOasisId(),FidTypeEnum.OASIS.getMark());
        // query collect account trans
        Transactions brandCollectAccountTran = teamTransactionsMapper.selectCollectAccountForTodayTrans(brandId,
                FidTypeEnum.BRAND.getMark(),dto.getOasisId(),FidTypeEnum.OASIS.getMark());


        // validate
        if(finDistribute==null || oasisFinAccount == null
        ){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(brandCollectAccountTran !=null){
            throw new ErrorCodeException(CodeEnum.COLLECT_LIMIT);
        }
        // 最大可提取金额为oasis 账户的1/10,brand 账户提取额度小于等于oasis 账户的1/10
        BigDecimal availableAmount= oasisFinAccount.getDrawable().divide(BigDecimal.valueOf(10));
        if(dto.getAmount().compareTo(finDistribute.getAmount())>0|| dto.getAmount().compareTo(availableAmount)>0
        ){
            throw new ErrorCodeException(CodeEnum.NO_SUFFICIENT_FUNDS);
        }


        TransferBO bo = defaultPayService.generateTransferBO(TransTypeEnum.OASIS_COLLECT_IN.getMark(),
                FidTypeEnum.BRAND.getMark(),brandId,FidTypeEnum.OASIS.getMark(),dto.getOasisId(),dto.getAmount(),dto.getOasisId());

        String payId = defaultPayService.transfer(new Gson().toJson(bo));


        // update oasis point
        baseOasisPointsService.deduct(oasis.getId(), brandId,dto.getAmount(),"收帐核销", OasisPointBusinessTypeEnum.DEDUCT.getMark(), payId, "支付返回编号："+payId);

    }

    @Override
    public void adminWithdrawFromOasis(TeamOasisAdminWithdrawDTO dto) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // query oasis info , only for admin
        Oasis oasis = teamOasisMapper.selectById(dto.getOasisId());
        if(oasis==null || !brandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(OasisMarkEnum.BLOCKED.getMark().equals(oasis.getMark())){
            throw new ErrorCodeException(CodeEnum.OASIS_LOCKED);
        }

        // query Oasis account, then check amount
        FinAccount oasisFinAccount = teamFinAccountMapper.selectOneByFidForUpdate(dto.getOasisId(),FidTypeEnum.OASIS.getMark());
        if(oasisFinAccount==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        if(dto.getAmount().compareTo(oasisFinAccount.getDrawable())>0){
            throw new ErrorCodeException(CodeEnum.NO_SUFFICIENT_FUNDS);
        }

        // transfer money to admin account
        TransferBO bo = generateTransferBOForAdminWithdraw(dto.getAmount(), dto.getOasisId(), dto.getOasisId(), brandId);
        defaultPayService.transfer(new Gson().toJson(bo));


    }

    private TransferBO generateTransferBOForAdminWithdraw(BigDecimal amount, String outNo,String payer,String payee){
        TransferBO bo = new TransferBO();
        bo.setAmount(amount)
                .setOutNo(outNo)
                .setPayeeType(FidTypeEnum.BRAND.getMark())
                .setPayeeAccount(payee)
                .setPayerAccount(payer)
                .setPayerType(FidTypeEnum.OASIS.getMark())
                .setTransType(TransTypeEnum.OASIS_ADMIN_WITHDRAW.getMark());
        return  bo;
    }
}
