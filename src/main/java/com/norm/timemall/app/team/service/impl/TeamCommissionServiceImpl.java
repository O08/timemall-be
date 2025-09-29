package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.service.BaseOasisPointsService;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.pojo.TeamFetchCommissionDetail;
import com.norm.timemall.app.team.domain.ro.TeamCommissionRO;
import com.norm.timemall.app.team.helper.TeamCommissionHelper;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamCommissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class TeamCommissionServiceImpl implements TeamCommissionService {

    @Autowired
    private TeamCommissionMapper teamCommissionMapper;

    @Autowired
    private TeamTransactionsMapper teamTransactionsMapper;

    @Autowired
    private TeamOasisJoinMapper teamOasisJoinMapper;
    @Autowired
    private TeamCommissionDeliverMapper teamCommissionDeliverMapper;

    @Autowired
    private BaseOasisPointsService baseOasisPointsService;




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

        if(commission==null ||
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
                  || commission.getTag().equals(OasisCommissionTagEnum.FINISH.getMark());

        if(validatedFail){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // tag task to finish
        teamCommissionMapper.updateCommissionByIdAndTag(
                dto.getCommissionId(),
                commission.getWorker(),
                OasisCommissionTagEnum.FINISH.getMark());

        // 2. update or insert fin_distribute
        teamCommissionHelper.createFinDistributeIfNotExists(commission.getOasisId(), commission.getWorker());
        baseOasisPointsService.topUp(commission.getOasisId(), commission.getWorker(),commission.getBonus(),"任务佣金", OasisPointBusinessTypeEnum.TOP_UP.getMark(), commission.getId(), "目标任务："+commission.getId());

        // 4. update oasis join
        updateOasisJoinModifiedAt(commission.getOasisId(), commission.getWorker());
        // 5. mark commission   deliver tag as delivered
        TeamPutCommissionDeliverTagDTO deliverTagDTO = new TeamPutCommissionDeliverTagDTO();
        deliverTagDTO.setCommissionId(dto.getCommissionId());
        deliverTagDTO.setDeliverId(dto.getDeliverId());
        deliverTagDTO.setTag(DeliverTagEnum.DELIVERED.getMark());
        teamCommissionDeliverMapper.updateTagById(deliverTagDTO);



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

        Commission commission = teamCommissionMapper.selectById(dto.getCommissionId());
        if(commission==null
          ||  !OasisCommissionTagEnum.CREATED.getMark().equals(commission.getTag())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        commission.setTag(dto.getTag())
                .setModifiedAt(new Date());
        teamCommissionMapper.updateById(commission);

    }

    @Override
    public void delOneCommission(String id) {

        List<String> scopes = Arrays.asList(OasisCommissionTagEnum.CREATED.getMark(),
                            OasisCommissionTagEnum.ADD_TO_NEED_POOL.getMark(),OasisCommissionTagEnum.ABOLISH.getMark(),
                            OasisCommissionTagEnum.FIND_NEW_SUPPLIER.getMark());

        LambdaQueryWrapper<Commission> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(Commission::getId,id)
                        .in(Commission::getTag,scopes);

        teamCommissionMapper.delete(wrapper);

    }

    @Override
    public void modifyCommission(TeamOasisChangeTaskDTO dto) {

        Commission commission= new Commission();
        commission.setId(dto.getId())
                .setTitle(dto.getTitle())
                .setBonus(dto.getBonus())
                .setSow(dto.getSow())
                .setModifiedAt(new Date());

        List<String> scopes = Arrays.asList(OasisCommissionTagEnum.CREATED.getMark(), OasisCommissionTagEnum.ADD_TO_NEED_POOL.getMark(),
                OasisCommissionTagEnum.FIND_NEW_SUPPLIER.getMark());

        LambdaQueryWrapper<Commission> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(Commission::getId,dto.getId())
                .in(Commission::getTag,scopes);
        teamCommissionMapper.update(commission,wrapper);

    }

    private void updateOasisJoinModifiedAt(String oasisId,String brandId){
        teamOasisJoinMapper.updateModifiedAtByBrandIdAndOasisId(oasisId,brandId);

    }
}
