package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.base.service.AccountService;
import com.norm.timemall.app.team.domain.dto.TeamAcceptOasisTaskDTO;
import com.norm.timemall.app.team.domain.dto.TeamCommissionDTO;
import com.norm.timemall.app.team.domain.dto.TeamFinishOasisTask;
import com.norm.timemall.app.team.domain.dto.TeamOasisNewTaskDTO;
import com.norm.timemall.app.team.domain.ro.TeamCommissionRO;
import com.norm.timemall.app.team.helper.TeamCommissionHelper;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Service
public class TeamCommissionServiceImpl implements TeamCommissionService {

    @Autowired
    private TeamCommissionMapper teamCommissionMapper;

    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;
    @Autowired
    private TeamAccountMapper teamAccountMapper;




    @Autowired
    private TeamCommissionHelper teamCommissionHelper;
    @Autowired
    private AccountService accountService;
    @Override
    public IPage<TeamCommissionRO> findCommission(TeamCommissionDTO dto) {
        IPage<TeamCommissionRO> page =  new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return  teamCommissionMapper.selectPageByFilter(page,dto);
    }

    @Override
    public void newOasisTask(TeamOasisNewTaskDTO dto) {
        String brandId = accountService.
                findBrandInfoByUserId(SecurityUserHelper.getCurrentPrincipal().getUserId())
                .getId();

        Commission commission =new Commission();
        commission.setId(IdUtil.simpleUUID())
                .setOasisId(dto.getOasisId())
                .setTitle(dto.getTitle())
                .setBonus(dto.getBonus())
                .setFounder(brandId)
                .setTag(OasisCommissionTagEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamCommissionMapper.insert(commission);

    }

    @Override
    public void acceptOasisTask(TeamAcceptOasisTaskDTO dto) {
        teamCommissionMapper.updateCommissionByIdAndTag(
                dto.getCommissionId(),
                dto.getBrandId(),
                OasisCommissionTagEnum.ACCEPT.getMark());
    }

    @Override
    public void finishOasisTask(TeamFinishOasisTask dto) {
        Commission commission = teamCommissionMapper.selectById(dto.getCommissionId());
        if(commission == null || commission.getTag().equals(OasisCommissionTagEnum.FINISH.getMark())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // tag task to finish
        teamCommissionMapper.updateCommissionByIdAndTag(
                dto.getCommissionId(),
                commission.getWorker(),
                OasisCommissionTagEnum.FINISH.getMark());
        // calculate finance info
        // 1.insert trans
        String transNo = IdUtil.simpleUUID();
        Transactions creditTrans = new Transactions();
        creditTrans.setId(IdUtil.simpleUUID())
                .setFid(commission.getWorker())
                .setFidType(FidTypeEnum.OASIS.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.COMMISSION.getMark())
                .setTransTypeDesc(TransTypeEnum.COMMISSION.getDesc())
                .setAmount(commission.getBonus())
                .setDirection(TransDirectionEnum.CREDIT.getMark())
                .setRemark(TransTypeEnum.COMMISSION.getDesc()+"-"+commission.getTitle())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        Transactions debitTrans = new Transactions();
        debitTrans.setId(IdUtil.simpleUUID())
                .setFid(commission.getOasisId())
                .setFidType(FidTypeEnum.OASIS.getMark())
                .setTransNo(transNo)
                .setTransType(TransTypeEnum.COMMISSION.getMark())
                .setTransTypeDesc(TransTypeEnum.COMMISSION.getDesc())
                .setAmount(commission.getBonus())
                .setDirection(TransDirectionEnum.DEBIT.getMark())
                .setRemark(TransTypeEnum.COMMISSION.getDesc()+"-"+commission.getTitle())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamTransactionsMapper.insert(creditTrans);
        teamTransactionsMapper.insert(debitTrans);
        // 2. update or insert fin_distribute
        teamCommissionHelper.UpdateFinDistribute(commission.getOasisId(), commission.getWorker(), commission.getBonus());
        // 3. update finAccount amount
        FinAccount brandFinAccount = teamAccountMapper.selectOneByFidForUpdate(commission.getWorker(),FidTypeEnum.BRAND.getMark());
        BigDecimal brandFinAccountAmountBalance = brandFinAccount.getAmount().add(commission.getBonus());
        brandFinAccount.setAmount(brandFinAccountAmountBalance);
        teamAccountMapper.updateById(brandFinAccount);


//        //2. calculate total
//        teamCommissionHelper.calTotalTransWhenFinishTask(commission.getWorker()
//                ,commission.getOasisId(),commission.getBonus());
//
//        //3. calculate month
//        teamCommissionHelper.calMonthTransWhenFinishTask(commission.getWorker()
//                ,commission.getOasisId(),commission.getBonus());






    }
}
