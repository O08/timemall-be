package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.OasisCommissionTagEnum;
import com.norm.timemall.app.base.enums.TransDirectionEnum;
import com.norm.timemall.app.base.enums.TransTypeEnum;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.AccountCalMonth;
import com.norm.timemall.app.base.mo.AccountCalTotal;
import com.norm.timemall.app.base.mo.Commission;
import com.norm.timemall.app.base.mo.Transactions;
import com.norm.timemall.app.base.security.CustomizeUser;
import com.norm.timemall.app.team.domain.dto.TeamAcceptOasisTaskDTO;
import com.norm.timemall.app.team.domain.dto.TeamCommissionDTO;
import com.norm.timemall.app.team.domain.dto.TeamFinishOasisTask;
import com.norm.timemall.app.team.domain.dto.TeamOasisNewTaskDTO;
import com.norm.timemall.app.team.domain.ro.TeamCommissionRO;
import com.norm.timemall.app.team.helper.TeamCommissionHelper;
import com.norm.timemall.app.team.mapper.TeamAccountCalMonthMapper;
import com.norm.timemall.app.team.mapper.TeamAccountCalTotalMapper;
import com.norm.timemall.app.team.mapper.TeamCommissionMapper;
import com.norm.timemall.app.team.mapper.TeamTransactionsMapper;
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
    private TeamCommissionHelper teamCommissionHelper;
    @Override
    public IPage<TeamCommissionRO> findCommission(TeamCommissionDTO dto) {
        IPage<TeamCommissionRO> page =  new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return  teamCommissionMapper.selectPageByFilter(page,dto);
    }

    @Override
    public void newOasisTask(TeamOasisNewTaskDTO dto) {
        CustomizeUser user = SecurityUserHelper.getCurrentPrincipal();

        Commission commission =new Commission();
        commission.setId(IdUtil.simpleUUID())
                .setOasisId(dto.getOasisId())
                .setTitle(dto.getTitle())
                .setBonus(dto.getBonus())
                .setFounder(user.getUserId())// todo userid change to brandId
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
        // tag task to finish
        teamCommissionMapper.updateCommissionByIdAndTag(
                dto.getCommissionId(),
                dto.getBrandId(),
                OasisCommissionTagEnum.FINISH.getMark());
        // calculate finance info
        // 1.insert trans
        String transNo = IdUtil.simpleUUID();
        Transactions creditTrans = new Transactions();
        creditTrans.setId(IdUtil.simpleUUID())
                .setFid(dto.getBrandId())
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
                .setFid(dto.getOasisId())
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

        //2. calculate total
        teamCommissionHelper.calTotalTransWhenFinishTask(dto.getBrandId()
                ,dto.getOasisId(),commission.getBonus());

        //3. calculate month
        teamCommissionHelper.calMonthTransWhenFinishTask(dto.getBrandId()
                ,dto.getOasisId(),commission.getBonus());






    }
}
