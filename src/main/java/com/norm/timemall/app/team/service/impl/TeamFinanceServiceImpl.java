package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FidTypeEnum;
import com.norm.timemall.app.base.enums.TransDirectionEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.FinAccount;
import com.norm.timemall.app.base.mo.FinDistribute;
import com.norm.timemall.app.base.mo.Transactions;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.domain.pojo.TeamFinDistriution;
import com.norm.timemall.app.team.domain.pojo.TeamFinDistriutionItem;
import com.norm.timemall.app.team.domain.ro.TeamFinBoardRO;
import com.norm.timemall.app.team.domain.ro.TeamObj2RO;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamFinanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

@Service
public class TeamFinanceServiceImpl implements TeamFinanceService {
    @Autowired
    private TeamMarketObjectMapper teamMarketObjectMapper;
    @Autowired
    private TeamAccountMapper teamAccountMapper;
    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;
    @Autowired
    private TeamMarketObjectRecordMapper teamMarketObjectRecordMapper;

    @Autowired
    private TeamFinDistributeMapper teamFinDistributeMapper;
    @Autowired
    private AccountService accountService;
    @Override
    public void orderObj(String objId) {
        String brandId =""; // todo
        //1.query obj info
        TeamObj2RO objRO = teamMarketObjectMapper.selectOneObj(objId);
        if(objRO==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        //2.validate user account extra,if pass ,go
        FinAccount customerFinAccount = teamAccountMapper.selectOneByFid(brandId, FidTypeEnum.BRAND.getMark());
        if(customerFinAccount ==null || customerFinAccount.getDrawable().compareTo(objRO.getSalePrice())<0){
            throw new ErrorCodeException(CodeEnum.NO_SUFFICIENT_FUNDS);
        }

        //  insert transactions for customer
        //  insert transactions for supplier
        String transNo = IdUtil.simpleUUID();
        Transactions creditTrans = new Transactions();
        creditTrans.setId(IdUtil.simpleUUID())
                .setFid(objRO.getCreditId())
                .setFidType(FidTypeEnum.BRAND.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.TRANSFER.getMark())
                .setTransTypeDesc(TransTypeEnum.TRANSFER.getDesc())
                .setAmount(objRO.getSalePrice())
                .setDirection(TransDirectionEnum.CREDIT.getMark())
                .setRemark(TransTypeEnum.TRANSFER.getDesc()+"-"+objRO.getTitle())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        Transactions debitTrans = new Transactions();
        debitTrans.setId(IdUtil.simpleUUID())
                .setFid(brandId)
                .setFidType(FidTypeEnum.BRAND.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.TRANSFER.getMark())
                .setTransTypeDesc(TransTypeEnum.TRANSFER.getDesc())
                .setAmount(objRO.getSalePrice())
                .setDirection(TransDirectionEnum.DEBIT.getMark())
                .setRemark(TransTypeEnum.TRANSFER.getDesc()+"-"+objRO.getTitle())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamTransactionsMapper.insert(creditTrans);
        teamTransactionsMapper.insert(debitTrans);

        //3.deduct user account
        BigDecimal balance = customerFinAccount.getDrawable().subtract(objRO.getSalePrice());
        customerFinAccount.setDrawable(balance);
        teamAccountMapper.updateById(customerFinAccount);
        //4.add obj owned account
        FinAccount supplierFinAccount = teamAccountMapper.selectOneByFid(objRO.getCreditId(),
                FidTypeEnum.BRAND.getMark());
        if(supplierFinAccount !=null){
            BigDecimal drawable = supplierFinAccount.getDrawable().add(objRO.getSalePrice());
            supplierFinAccount.setDrawable(drawable);
            teamAccountMapper.updateById(supplierFinAccount);
        }
        if(supplierFinAccount ==null){
            supplierFinAccount = new FinAccount();
            supplierFinAccount.setId(IdUtil.simpleUUID())
                    .setFid(objRO.getCreditId())
                    .setFidType(FidTypeEnum.BRAND.getMark())
                    .setDrawable(objRO.getSalePrice())
                    .setAmount(BigDecimal.ZERO);
            teamAccountMapper.insert(supplierFinAccount);
        }

        // 5. update obj record info
          teamMarketObjectRecordMapper.updateCreditIdById(objId,brandId);
        // 6. update cal tbs todo

    }

    @Override
    public TeamFinBoardRO kanban() {
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();
        FinAccount account = teamAccountMapper.selectOneByFid(brandId, FidTypeEnum.BRAND.getMark());
        TeamFinBoardRO ro = new TeamFinBoardRO();
        ro.setAmount(account==null ? BigDecimal.ZERO : account.getAmount());
        ro.setDrawable(account==null? BigDecimal.ZERO :account.getDrawable());
        return ro;
    }

    @Override
    public TeamFinDistriution findFinDistribution() {
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();
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
