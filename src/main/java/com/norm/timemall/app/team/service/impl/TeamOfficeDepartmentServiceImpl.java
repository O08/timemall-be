package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Oasis;
import com.norm.timemall.app.base.mo.OfficeDepartment;
import com.norm.timemall.app.base.mo.OfficeEmployee;
import com.norm.timemall.app.base.util.mate.MybatisMateEncryptor;
import com.norm.timemall.app.team.domain.dto.TeamOfficeChangeDepartmentDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeNewDepartmentDTO;
import com.norm.timemall.app.team.domain.dto.TeamOfficeQueryDepartmentPageDTO;
import com.norm.timemall.app.team.domain.ro.TeamOfficeQueryDepartmentPageRO;
import com.norm.timemall.app.team.mapper.TeamOasisMapper;
import com.norm.timemall.app.team.mapper.TeamOfficeDepartmentMapper;
import com.norm.timemall.app.team.mapper.TeamOfficeEmployeeMapper;
import com.norm.timemall.app.team.service.TeamOfficeDepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeamOfficeDepartmentServiceImpl implements TeamOfficeDepartmentService {
    @Autowired
    private TeamOfficeDepartmentMapper teamOfficeDepartmentMapper;

    @Autowired
    private TeamOfficeEmployeeMapper teamOfficeEmployeeMapper;


    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private MybatisMateEncryptor mybatisMateEncryptor;

    @Override
    public IPage<TeamOfficeQueryDepartmentPageRO> findDepartments(TeamOfficeQueryDepartmentPageDTO dto) {
        IPage<TeamOfficeQueryDepartmentPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        if(CharSequenceUtil.isNotBlank(dto.getQ())){
            dto.setEncryptedQ(mybatisMateEncryptor.defaultEncrypt(dto.getQ()));
        }
        return teamOfficeDepartmentMapper.selectPageByQ(page,dto);
    }

    @Override
    public void createDepartment(TeamOfficeNewDepartmentDTO dto) {

        validateUserIsAdmin(dto.getOasisId());
        LambdaQueryWrapper<OfficeDepartment> departmentLambdaQueryWrapper=Wrappers.lambdaQuery();
        departmentLambdaQueryWrapper.eq(OfficeDepartment::getOasisId,dto.getOasisId());
        departmentLambdaQueryWrapper.eq(OfficeDepartment::getTitle,dto.getTitle());

        boolean titleExists = teamOfficeDepartmentMapper.exists(departmentLambdaQueryWrapper);
        if(titleExists){
            throw new QuickMessageException("部门已存在");
        }

        OfficeDepartment department=new OfficeDepartment();
        department.setId(IdUtil.simpleUUID())
                .setTitle(dto.getTitle())
                .setTotalStaff(0)
                .setDescription(dto.getDescription())
                .setOasisId(dto.getOasisId())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamOfficeDepartmentMapper.insert(department);

    }

    @Override
    public void editDepartment(TeamOfficeChangeDepartmentDTO dto) {
        OfficeDepartment department = teamOfficeDepartmentMapper.selectById(dto.getDepartmentId());
        if(department==null){
            throw new QuickMessageException("未找到部门数据");
        }
        validateUserIsAdmin(department.getOasisId());

        // if leader not blank,validate
        if(CharSequenceUtil.isNotBlank(dto.getLeaderEmployeeId())){
            OfficeEmployee officeEmployee = teamOfficeEmployeeMapper.selectById(dto.getLeaderEmployeeId());
            if(officeEmployee==null){
                throw new QuickMessageException("未找到部门领导数据");
            }
            if(!department.getOasisId().equals(officeEmployee.getOasisId())){
                throw new QuickMessageException("部门领导校验不通过");
            }
        }

        department.setLeaderEmployeeId(dto.getLeaderEmployeeId());
        department.setTitle(dto.getTitle());
        department.setDescription(dto.getDescription());
        department.setModifiedAt(new Date());

        teamOfficeDepartmentMapper.updateById(department);
    }

    @Override
    public void delOneDepartment(String id) {
        OfficeDepartment department = teamOfficeDepartmentMapper.selectById(id);
        if(department==null){
            throw new QuickMessageException("未找到部门数据");
        }
        validateUserIsAdmin(department.getOasisId());

        LambdaQueryWrapper<OfficeEmployee> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(OfficeEmployee::getDepartmentId,id);
        boolean existsEmployee = teamOfficeEmployeeMapper.exists(wrapper);
        if(existsEmployee){
            throw new QuickMessageException("存在归属该部门员工");
        }

        teamOfficeDepartmentMapper.deleteById(id);
    }

    private void validateUserIsAdmin(String oasisId){
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        // role check,only admin can create
        Oasis oasis = teamOasisMapper.selectById(oasisId);
        if(oasis==null){
            throw new QuickMessageException("未找到相关社群或频道数据");
        }
        if(!currentBrandId.equals(oasis.getInitiatorId())){
            throw new ErrorCodeException(CodeEnum.USER_ROLE_NOT_CORRECT);
        }
    }
}
