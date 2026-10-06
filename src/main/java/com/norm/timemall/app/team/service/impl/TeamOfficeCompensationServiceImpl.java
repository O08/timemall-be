package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.mo.OfficeCompensation;
import com.norm.timemall.app.base.mo.OfficeEmployeeCompensation;
import com.norm.timemall.app.team.domain.dto.TeamOfficeChangeCompensationDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeCreateCompensationDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryCompensationPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryCompensationPageRO;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import com.norm.timemall.app.team.mapper.TeamOfficeCompensationMapper;
import com.norm.timemall.app.team.mapper.TeamOfficeEmployeeCompensationMapper;
import com.norm.timemall.app.team.service.TeamOfficeCompensationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeamOfficeCompensationServiceImpl implements TeamOfficeCompensationService {
    @Autowired
    private TeamOfficeCompensationMapper teamOfficeCompensationMapper;

    @Autowired
    private TeamOfficeEmployeeCompensationMapper teamOfficeEmployeeCompensationMapper;

    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Override
    public IPage<TeamOfficeQueryCompensationPageRO> findCompensations(TeamOfficeQueryCompensationPageDTO dto) {
        validateUserIsAdmin(dto.getOasisId());
        IPage<TeamOfficeQueryCompensationPageRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamOfficeCompensationMapper.selectPageByQ(page,dto);
    }

    @Override
    public void addOneCompensation(TeamOfficeCreateCompensationDTO dto) {
        validateUserIsAdmin(dto.getOasisId());
        LambdaQueryWrapper<OfficeCompensation> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(OfficeCompensation::getTitle,dto.getTitle())
                .eq(OfficeCompensation::getOasisId,dto.getOasisId());

        boolean titleAlreadyExists = teamOfficeCompensationMapper.exists(wrapper);
        if(titleAlreadyExists){
            throw new QuickMessageException("条目已经配置");
        }
        OfficeCompensation compensation= new OfficeCompensation();
        compensation.setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setDescription(dto.getDescription())
                .setOasisId(dto.getOasisId())
                .setDirection(dto.getDirection())
                .setAmount(dto.getAmount())
                .setStatus(SwitchCheckEnum.ENABLE.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamOfficeCompensationMapper.insert(compensation);

    }

    @Override
    public void editCompensation(TeamOfficeChangeCompensationDTO dto) {
        OfficeCompensation compensation = teamOfficeCompensationMapper.selectById(dto.getId());
        if(compensation==null){
            throw new QuickMessageException("未找到相关数据");
        }

        validateUserIsAdmin(compensation.getOasisId());

        compensation.setTitle(dto.getTitle())
                .setDescription(dto.getDescription())
                .setAmount(dto.getAmount())
                .setStatus(dto.getStatus())
                .setModifiedAt(new Date());

        teamOfficeCompensationMapper.updateById(compensation);


    }

    @Override
    public void delOneCompensation(String id) {
        OfficeCompensation compensation = teamOfficeCompensationMapper.selectById(id);
        if(compensation==null){
            throw new QuickMessageException("未找到相关数据");
        }
        validateUserIsAdmin(compensation.getOasisId());

        // if employee using, return
        LambdaQueryWrapper<OfficeEmployeeCompensation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(OfficeEmployeeCompensation::getCompensationId,id);

        boolean existsEmployeeUsing = teamOfficeEmployeeCompensationMapper.exists(queryWrapper);
        if(existsEmployeeUsing){
            throw new QuickMessageException("条目已被使用");
        }

        teamOfficeCompensationMapper.deleteById(id);



    }

    private void validateUserIsAdmin(String oasisId){
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // role check,only admin can continue
        Oasis oasis = teamOasisMapper.selectById(oasisId);
        if(oasis==null){
            throw new QuickMessageException("未找到相关社群或频道数据");
        }
        if(!currentBrandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
    }
}
