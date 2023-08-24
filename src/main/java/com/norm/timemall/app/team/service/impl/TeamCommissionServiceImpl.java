package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.pojo.TeamFetchCommissionDetail;
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
    private TeamOasisJoinMapper teamOasisJoinMapper;
    @Autowired
    private TeamCommissionDeliverMapper teamCommissionDeliverMapper;




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
        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();

        Commission commission =new Commission();
        commission.setId(IdUtil.simpleUUID())
                .setOasisId(dto.getOasisId())
                .setTitle(dto.getTitle())
                .setBonus(dto.getBonus())
                .setSow(dto.getSow())
                .setFounder(brandId)
                .setTag(OasisCommissionTagEnum.CREATED.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamCommissionMapper.insert(commission);
        // update oasis join
        updateOasisJoinModifiedAt(dto.getOasisId(),brandId);


    }

    @Override
    public void acceptOasisTask(TeamAcceptOasisTaskDTO dto) {

        String brandId = SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Commission commission = teamCommissionMapper.selectById(dto.getCommissionId());

        if(commission==null || brandId.equals(commission.getFounder()) ||
           !OasisCommissionTagEnum.ADD_TO_NEED_POOL.getMark().equals(commission.getTag())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        teamCommissionMapper.updateCommissionByIdAndTag(
                dto.getCommissionId(),
                brandId,
                OasisCommissionTagEnum.ACCEPT.getMark());

    }

    @Override
    public void finishOasisTask(TeamFinishOasisTaskDTO dto) {
        Commission commission = teamCommissionMapper.selectById(dto.getCommissionId());
        boolean validatedFail= commission == null
                  || commission.getTag().equals(OasisCommissionTagEnum.FINISH.getMark())
                  || !commission.getFounder().equals(SecurityUserHelper.getCurrentPrincipal().getBrandId());

        if(validatedFail){
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
                .setFidType(FidTypeEnum.BRAND.getMark())
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
        // 4. update oasis join
        updateOasisJoinModifiedAt(commission.getOasisId(), commission.getWorker());
        // 5. mark commission   deliver tag as delivered
        TeamPutCommissionDeliverTagDTO deliverTagDTO = new TeamPutCommissionDeliverTagDTO();
        deliverTagDTO.setCommissionId(dto.getCommissionId());
        deliverTagDTO.setDeliverId(dto.getDeliverId());
        deliverTagDTO.setTag(DeliverTagEnum.DELIVERED.getMark());
        teamCommissionDeliverMapper.updateTagById(deliverTagDTO);


//        //2. calculate total
//        teamCommissionHelper.calTotalTransWhenFinishTask(commission.getWorker()
//                ,commission.getOasisId(),commission.getBonus());
//
//        //3. calculate month
//        teamCommissionHelper.calMonthTransWhenFinishTask(commission.getWorker()
//                ,commission.getOasisId(),commission.getBonus());

    }

    @Override
    public TeamFetchCommissionDetail findCommissionDetail(String id) {
        return teamCommissionMapper.selectCommissionDetailById(id);
    }

    @Override
    public Commission findCommissionUsingId(String commissionId) {
        return teamCommissionMapper.selectById(commissionId);
    }

    @Override
    public void examineOasisTask(TeamExamineOasisTaskDTO dto) {

        String brandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        Commission commission = teamCommissionMapper.selectById(dto.getCommissionId());
        if(commission==null
          || !brandId.equals(commission.getFounder())
          || !OasisCommissionTagEnum.CREATED.getMark().equals(commission.getTag())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        commission.setTag(dto.getTag())
                .setModifiedAt(new Date());
        teamCommissionMapper.updateById(commission);

    }

    private void updateOasisJoinModifiedAt(String oasisId,String brandId){
        teamOasisJoinMapper.updateModifiedAtByBrandIdAndOasisId(oasisId,brandId);

    }
}
