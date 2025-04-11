package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.google.gson.Gson;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.TransDirectionEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.FinDistribute;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.mo.Transactions;
import com.norm.timemall.app.base.pojo.TransferBO;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.pay.service.DefaultPayService;
import com.norm.timemall.app.team.domain.dto.TeamOasisAdminWithdrawDTO;
import com.norm.timemall.app.team.domain.dto.TeamOasisCollectAccountDTO;
import com.norm.timemall.app.team.mapper.TeamAccountMapper;
import com.norm.timemall.app.team.mapper.TeamFinDistributeMapper;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import com.norm.timemall.app.team.mapper.TeamTransactionsMapper;
import com.norm.timemall.app.team.service.TeamOasisCollectAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TeamOasisCollectAccountServiceImpl implements TeamOasisCollectAccountService {
    @Autowired
    private TeamAccountMapper teamAccountMapper;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;
    @Autowired
    private TeamFinDistributeMapper teamFinDistributeMapper;

    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private DefaultPayService defaultPayService;

    @Transactional
    @Override
    public void collectAccountFromOasis(TeamOasisCollectAccountDTO dto)
    {
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();
        FinDistribute finDistribute= teamFinDistributeMapper.selectDistributeByBrandIdAndOasisId(brandId,dto.getOasisId());
        // query brand account
        FinAccount brandFinAccount = teamAccountMapper.selectOneByFid(brandId, FidTypeEnum.BRAND.getMark());
        // query Oasis account
        FinAccount oasisFinAccount = teamAccountMapper.selectOneByFidForUpdate(dto.getOasisId(),FidTypeEnum.OASIS.getMark());
        // query collect account trans
        Transactions brandCollectAccountTran = teamTransactionsMapper.selectCollectAccountForTodayTrans(brandId,
                FidTypeEnum.BRAND.getMark(),dto.getOasisId(),FidTypeEnum.OASIS.getMark());


        // validate
        if(finDistribute==null || brandFinAccount == null || oasisFinAccount == null
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


        // insert trans
        String transNo = IdUtil.simpleUUID();
        Transactions creditTrans = new Transactions();
        creditTrans.setId(IdUtil.simpleUUID())
                .setFid(brandId)
                .setFidType(FidTypeEnum.BRAND.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.OASIS_COLLECT_IN.getMark())
                .setTransTypeDesc(TransTypeEnum.OASIS_COLLECT_IN.getDesc())
                .setAmount(dto.getAmount())
                .setDirection(TransDirectionEnum.CREDIT.getMark())
                .setRemark(TransTypeEnum.OASIS_COLLECT_IN.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        Transactions debitTrans = new Transactions();
        debitTrans.setId(IdUtil.simpleUUID())
                .setFid(dto.getOasisId())
                .setFidType(FidTypeEnum.OASIS.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.OASIS_COLLECT_IN.getMark())
                .setTransTypeDesc(TransTypeEnum.OASIS_COLLECT_IN.getDesc())
                .setAmount(dto.getAmount())
                .setDirection(TransDirectionEnum.DEBIT.getMark())
                .setRemark(TransTypeEnum.OASIS_COLLECT_IN.getDesc())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamTransactionsMapper.insert(creditTrans);
        teamTransactionsMapper.insert(debitTrans);

        // action option
        BigDecimal brandBalance = brandFinAccount.getDrawable().add(dto.getAmount());
        brandFinAccount.setDrawable(brandBalance);
        teamAccountMapper.updateById(brandFinAccount);

        BigDecimal oasisBalance = oasisFinAccount.getDrawable().subtract(dto.getAmount());
        oasisFinAccount.setDrawable(oasisBalance);
        teamAccountMapper.updateById(oasisFinAccount);

        // update fin_distribute
        BigDecimal distributeBalance= finDistribute.getAmount().subtract(dto.getAmount());
        finDistribute.setAmount(distributeBalance);
        teamFinDistributeMapper.updateById(finDistribute);
    }

    @Override
    public void adminWithdrawFromOasis(TeamOasisAdminWithdrawDTO dto) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        // query oasis info , only for admin
        Oasis oasis = teamOasisMapper.selectById(dto.getOasisId());
        if(oasis==null || !brandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // query Oasis account, then check amount
        FinAccount oasisFinAccount = teamAccountMapper.selectOneByFidForUpdate(dto.getOasisId(),FidTypeEnum.OASIS.getMark());
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
