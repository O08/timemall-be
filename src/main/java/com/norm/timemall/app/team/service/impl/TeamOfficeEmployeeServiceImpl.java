package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.entity.Customer;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.OfficeEmployeeStatusEnum;
import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.CustomerMapper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.base.util.mate.MybatisMateEncryptor;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.*;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamOfficeEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TeamOfficeEmployeeServiceImpl implements TeamOfficeEmployeeService {
    @Autowired
    private TeamOfficeEmployeeMapper teamOfficeEmployeeMapper;

    @Autowired
    private TeamOfficeDepartmentMapper teamOfficeDepartmentMapper;

    @Autowired
    private TeamOfficeEmployeeBenefitMapper teamOfficeEmployeeBenefitMapper;

    @Autowired
    private TeamOfficeEmployeeCompensationMapper teamOfficeEmployeeCompensationMapper;

    @Autowired
    private TeamOfficeEmployeeMaterialMapper teamOfficeEmployeeMaterialMapper;

    @Autowired
    private TeamOfficeEmployeeKvMapper teamOfficeEmployeeKvMapper;

    @Autowired
    private TeamOfficeCompensationMapper teamOfficeCompensationMapper;

    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private TeamBrandMapper teamBrandMapper;

    @Autowired
    private MybatisMateEncryptor mybatisMateEncryptor;

    @Override
    public IPage<TeamOfficeQueryEmployeePageRO> findEmployees(TeamOfficeQueryEmployeePageDTO dto) {
        validateUserIsAdmin(dto.getOasisId());
        IPage<TeamOfficeQueryEmployeePageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        if(CharSequenceUtil.isNotBlank(dto.getQ())){
            dto.setEncryptedQ(mybatisMateEncryptor.defaultEncrypt(dto.getQ()));
        }
        return teamOfficeEmployeeMapper.selectPageByQ(page,dto);
    }

    @Override
    public void addOneEmployee(TeamOfficeTypeInEmployeeDTO dto) {
        OfficeDepartment department = teamOfficeDepartmentMapper.selectById(dto.getDepartmentId());
        if(department==null){
            throw new QuickMessageException("未找到部门数据");
        }

        validateUserIsAdmin(department.getOasisId());

        // validate uid
        Customer employee = customerMapper.selectById(dto.getUid());
        if(employee==null){
            throw new QuickMessageException("无效uid，请检查");
        }

        // validate employee : if employee already type in , can't join
        LambdaQueryWrapper<OfficeEmployee> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(OfficeEmployee::getOasisId,department.getOasisId())
                .eq(OfficeEmployee::getUid,dto.getUid());

        boolean alreadyTypeIn = teamOfficeEmployeeMapper.exists(wrapper);
        if(alreadyTypeIn){
            throw new QuickMessageException("职工已录入，请勿重复操作");
        }


        // find brand id
        Brand brand = teamBrandMapper.selectByUid(dto.getUid());

        String employeeId = doCreateEmployee(dto, department.getOasisId(), brand.getId());

        doCreateEmployeeBenefit(dto,employeeId);

    }

    @Override
    public TeamOfficeFetchEmployeeBasicInfoRO findEmployeeBasicInfo(String id) {
        validateCurrentUserIsEmployeeAdmin(id);
        return teamOfficeEmployeeMapper.selectBasicInfoById(id);
    }

    @Override
    public OfficeEmployeeBenefitRO findEmployeeBenefitInfo(String id) {
        validateCurrentUserIsEmployeeAdmin(id);
        return teamOfficeEmployeeBenefitMapper.selectOneBenefitByEmployeeId(id);
    }

    @Override
    public IPage<TeamOfficeFetchEmployeeCompensationInfoPageRO> findEmployeeCompensationInfo(TeamOfficeFetchEmployeeCompensationInfoPageDTO dto) {
        validateCurrentUserIsEmployeeAdmin(dto.getId());
        IPage<TeamOfficeFetchEmployeeCompensationInfoPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamOfficeEmployeeCompensationMapper.selectEmployeeCompensationInfoByEmployeeId(page,dto);
    }

    @Override
    public IPage<TeamOfficeFetchEmployeeMaterialPageRO> findEmployeeMaterialInfo(TeamOfficeFetchEmployeeMaterialPageDTO dto) {
        validateCurrentUserIsEmployeeAdmin(dto.getId());
        IPage<TeamOfficeFetchEmployeeMaterialPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamOfficeEmployeeMaterialMapper.selectPageByEmployeeId(page,dto);
    }

    @Override
    public IPage<TeamOfficeFetchEmployeeKvPageRO> findEmployeeKvInfo(TeamOfficeFetchEmployeeKvPageDTO dto) {
        validateCurrentUserIsEmployeeAdmin(dto.getId());
        IPage<TeamOfficeFetchEmployeeKvPageRO>  page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamOfficeEmployeeKvMapper.selectPageByEmployeeId(page,dto);
    }


    @Override
    public void doEditEmployeeBasicInfo(TeamOfficeEditEmployeeBasicInfoDTO dto) {
        OfficeEmployee employee = teamOfficeEmployeeMapper.selectById(dto.getEmployeeId());
        if(employee==null){
            throw new QuickMessageException("未找到相关员工数据");
        }
        validateUserIsAdmin(employee.getOasisId());

        OfficeDepartment department = teamOfficeDepartmentMapper.selectById(dto.getDepartmentId());
        if(department==null){
            throw new QuickMessageException("未找到部门数据");
        }
        if(!department.getOasisId().equals(employee.getOasisId())){
            throw new QuickMessageException("部门校验不通过");
        }


        LambdaUpdateWrapper<OfficeEmployee> updateWrapper=Wrappers.lambdaUpdate();
        updateWrapper.eq(OfficeEmployee::getId,employee.getId());

        updateWrapper
                .set(OfficeEmployee::getEmployeeNumber,dto.getEmployeeNumber())
                .set(OfficeEmployee::getEmployeeName,dto.getEmployeeName())
                .set(OfficeEmployee::getGender,dto.getGender())
                .set(OfficeEmployee::getBirthdate,dto.getBirthdate())
                .set(OfficeEmployee::getEducation,dto.getEducation())
                .set(OfficeEmployee::getMajor,dto.getMajor())
                .set(OfficeEmployee::getPhone,dto.getPhone())
                .set(OfficeEmployee::getEmail,dto.getEmail())
                .set(OfficeEmployee::getDepartmentId,dto.getDepartmentId())
                .set(OfficeEmployee::getRole,dto.getRole())
                .set(OfficeEmployee::getLevel,dto.getLevel())
                .set(OfficeEmployee::getOfficeLocation,dto.getOfficeLocation())
                .set(OfficeEmployee::getHireDate,dto.getHireDate())
                .set(OfficeEmployee::getNetWorth,dto.getNetWorth())
                .set(OfficeEmployee::getStatus,dto.getStatus())
                .set(OfficeEmployee::getGenre,dto.getGenre())
                .set(OfficeEmployee::getSalary,dto.getSalary())
                .set(OfficeEmployee::getRemark,dto.getRemark())
                .set(OfficeEmployee::getModifiedAt,new Date());


        teamOfficeEmployeeMapper.update(updateWrapper);

    }

    @Override
    public void doEditEmployeeBenefitInfo(TeamOfficeEditEmployeeBenefitInfoDTO dto) {
        OfficeEmployee employee = teamOfficeEmployeeMapper.selectById(dto.getEmployeeId());
        if(employee==null){
            throw new QuickMessageException("未找到相关员工数据");
        }
        validateUserIsAdmin(employee.getOasisId());

        LambdaQueryWrapper<OfficeEmployeeBenefit> benefitLambdaQueryWrapper=Wrappers.lambdaQuery();
        benefitLambdaQueryWrapper.eq(OfficeEmployeeBenefit::getEmployeeId,dto.getEmployeeId());
        OfficeEmployeeBenefit benefit = teamOfficeEmployeeBenefitMapper.selectOne(benefitLambdaQueryWrapper);
        if(benefit==null){
            throw new QuickMessageException("未找到相关五险一金数据");
        }

        benefit.setPensionInsuranceBase(dto.getPensionInsuranceBase())
                .setPensionInsuranceCompanyRate(dto.getPensionInsuranceCompanyRate())
                .setPensionInsuranceEmployeeRate(dto.getPensionInsuranceEmployeeRate())
                .setMedicalInsuranceBase(dto.getMedicalInsuranceBase())
                .setMedicalInsuranceCompanyRate(dto.getMedicalInsuranceCompanyRate())
                .setMedicalInsuranceEmployeeRate(dto.getMedicalInsuranceEmployeeRate())
                .setUnemploymentInsuranceBase(dto.getUnemploymentInsuranceBase())
                .setUnemploymentInsuranceCompanyRate(dto.getUnemploymentInsuranceCompanyRate())
                .setUnemploymentInsuranceEmployeeRate(dto.getUnemploymentInsuranceEmployeeRate())
                .setOccupationalInjuryInsuranceBase(dto.getOccupationalInjuryInsuranceBase())
                .setOccupationalInjuryInsuranceCompanyRate(dto.getOccupationalInjuryInsuranceCompanyRate())
                .setOccupationalInjuryInsuranceEmployeeRate(dto.getOccupationalInjuryInsuranceEmployeeRate())
                .setBirthInsuranceBase(dto.getBirthInsuranceBase())
                .setBirthInsuranceCompanyRate(dto.getBirthInsuranceCompanyRate())
                .setBirthInsuranceEmployeeRate(dto.getBirthInsuranceEmployeeRate())
                .setHousingProvidentFundsBase(dto.getHousingProvidentFundsBase())
                .setHousingProvidentFundsCompanyRate(dto.getHousingProvidentFundsCompanyRate())
                .setHousingProvidentFundsEmployeeRate(dto.getHousingProvidentFundsEmployeeRate())
                .setModifiedAt(new Date());

        teamOfficeEmployeeBenefitMapper.updateById(benefit);

    }

    @Override
    public IPage<TeamOfficeFetchEmployeeCompensationConfigInfoPageRO> findEmployeeCompensationConfigInfo(TeamOfficeFetchEmployeeCompensationConfigInfoPageDTO dto) {
        validateCurrentUserIsEmployeeAdmin(dto.getEmployeeId());
        IPage<TeamOfficeFetchEmployeeCompensationConfigInfoPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        return teamOfficeEmployeeCompensationMapper.selectEmployeeCompensationConfigInfoByEmployeeId(page,dto);

    }

    @Override
    public void doConfigEmployeeCompensation(TeamOfficeConfigEmployeeCompensationDTO dto) {

        OfficeEmployee employee = teamOfficeEmployeeMapper.selectById(dto.getEmployeeId());
        if(employee==null){
            throw new QuickMessageException("未找到相关员工数据");
        }
        validateUserIsAdmin(employee.getOasisId());
        OfficeCompensation compensationItem = teamOfficeCompensationMapper.selectById(dto.getCompensationId());
        if(compensationItem==null){
            throw new QuickMessageException("未找到相关薪资项数据");
        }
        if(!employee.getOasisId().equals(compensationItem.getOasisId())){
            throw new QuickMessageException("薪资项校验不通过");
        }
        LambdaQueryWrapper<OfficeEmployeeCompensation> delWrapper=Wrappers.lambdaQuery();
        delWrapper.eq(OfficeEmployeeCompensation::getEmployeeId,dto.getEmployeeId())
                .eq(OfficeEmployeeCompensation::getCompensationId,dto.getCompensationId());
        teamOfficeEmployeeCompensationMapper.delete(delWrapper);

        if(SwitchCheckEnum.ENABLE.getMark().equals(dto.getAssigned())){
            OfficeEmployeeCompensation compensation =new OfficeEmployeeCompensation();
            compensation.setId(IdUtil.simpleUUID())
                    .setEmployeeId(dto.getEmployeeId())
                    .setCompensationId(dto.getCompensationId())
                    .setCreateAt(new Date())
                    .setModifiedAt(new Date());
            teamOfficeEmployeeCompensationMapper.insert(compensation);
        }

    }

    @Override
    public void doSaveEmployeeMaterialInfo(String employeeId, String materialName, String materialUri,Long size) {
        OfficeEmployeeMaterial material=new OfficeEmployeeMaterial();
        material.setId(IdUtil.simpleUUID())
                .setMaterialName(materialName)
                .setMaterialUri(materialUri)
                .setSize(size)
                .setEmployeeId(employeeId)
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOfficeEmployeeMaterialMapper.insert(material);
    }

    @Override
    public void validateCurrentUserIsEmployeeAdmin(String employeeId) {
        OfficeEmployee employee = teamOfficeEmployeeMapper.selectById(employeeId);
        if(employee==null){
            throw new QuickMessageException("未找到相关员工数据");
        }
        validateUserIsAdmin(employee.getOasisId());
    }

    @Override
    public void addEmployeeKvPair(TeamOfficeCreateEmployeeKvPairDTO dto) {
        OfficeEmployeeKv kv = new OfficeEmployeeKv();
        kv.setId(IdUtil.simpleUUID())
                .setEmployeeId(dto.getEmployeeId())
                .setTitle(dto.getTitle())
                .setContent(dto.getContent())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());
        teamOfficeEmployeeKvMapper.insert(kv);
    }

    @Override
    public void doRemoveEmployeeKvPair(String id) {
        OfficeEmployeeKv kv = teamOfficeEmployeeKvMapper.selectById(id);
        if(kv==null){
            throw new QuickMessageException("未找到相关数据");
        }
        validateCurrentUserIsEmployeeAdmin(kv.getEmployeeId());
        teamOfficeEmployeeKvMapper.deleteById(id);
    }

    @Override
    public OfficeEmployeeMaterial findMaterialInfo(String id) {

        return  teamOfficeEmployeeMaterialMapper.selectById(id);
    }

    @Override
    public void doRemoveEmployeeMaterial(String id) {
        teamOfficeEmployeeMaterialMapper.deleteById(id);
    }

    @Override
    public void remarkEmployeeMaterial(TeamOfficeRemarkEmployeeMaterialDTO dto) {
        OfficeEmployeeMaterial material=teamOfficeEmployeeMaterialMapper.selectById(dto.getId());
        if(material==null){
            throw new QuickMessageException("未找到相关员工材料");
        }
        validateCurrentUserIsEmployeeAdmin(material.getEmployeeId());

        material.setRemark(dto.getRemark());
        material.setModifiedAt(new Date());
        teamOfficeEmployeeMaterialMapper.updateById(material);
    }

    @Override
    public void renameEmployeeMaterial(TeamOfficeRenameEmployeeMaterialDTO dto) {
        OfficeEmployeeMaterial material=teamOfficeEmployeeMaterialMapper.selectById(dto.getId());
        if(material==null){
            throw new QuickMessageException("未找到相关员工材料");
        }
        validateCurrentUserIsEmployeeAdmin(material.getEmployeeId());
        material.setMaterialName(dto.getMaterialName());
        material.setModifiedAt(new Date());
        teamOfficeEmployeeMaterialMapper.updateById(material);
    }

    private void doCreateEmployeeBenefit(TeamOfficeTypeInEmployeeDTO dto,String employeeId){
        OfficeEmployeeBenefit benefit = new OfficeEmployeeBenefit();
        benefit.setId(IdUtil.simpleUUID())
                .setEmployeeId(employeeId)
                .setPensionInsuranceBase(dto.getPensionInsuranceBase())
                .setPensionInsuranceCompanyRate(dto.getPensionInsuranceCompanyRate())
                .setPensionInsuranceEmployeeRate(dto.getPensionInsuranceEmployeeRate())
                .setMedicalInsuranceBase(dto.getMedicalInsuranceBase())
                .setMedicalInsuranceCompanyRate(dto.getMedicalInsuranceCompanyRate())
                .setMedicalInsuranceEmployeeRate(dto.getMedicalInsuranceEmployeeRate())
                .setUnemploymentInsuranceBase(dto.getUnemploymentInsuranceBase())
                .setUnemploymentInsuranceCompanyRate(dto.getUnemploymentInsuranceCompanyRate())
                .setUnemploymentInsuranceEmployeeRate(dto.getUnemploymentInsuranceEmployeeRate())
                .setOccupationalInjuryInsuranceBase(dto.getOccupationalInjuryInsuranceBase())
                .setOccupationalInjuryInsuranceCompanyRate(dto.getOccupationalInjuryInsuranceCompanyRate())
                .setOccupationalInjuryInsuranceEmployeeRate(dto.getOccupationalInjuryInsuranceEmployeeRate())
                .setBirthInsuranceBase(dto.getBirthInsuranceBase())
                .setBirthInsuranceCompanyRate(dto.getBirthInsuranceCompanyRate())
                .setBirthInsuranceEmployeeRate(dto.getBirthInsuranceEmployeeRate())
                .setHousingProvidentFundsBase(dto.getHousingProvidentFundsBase())
                .setHousingProvidentFundsCompanyRate(dto.getHousingProvidentFundsCompanyRate())
                .setHousingProvidentFundsEmployeeRate(dto.getHousingProvidentFundsEmployeeRate())
                .setCreateAt(new Date())
                .setModifiedAt(new Date())
        ;

        teamOfficeEmployeeBenefitMapper.insert(benefit);

    }

    private String doCreateEmployee(TeamOfficeTypeInEmployeeDTO dto,String oasisId,String employeeBrandId){
        String employeeId = IdUtil.simpleUUID();
        OfficeEmployee employee = new OfficeEmployee();
        employee.setId(employeeId)
                .setOasisId(oasisId)
                .setUid(dto.getUid())
                .setEmployeeBrandId(employeeBrandId)
                .setEmployeeNumber(dto.getEmployeeNumber())
                .setEmployeeName(dto.getEmployeeName())
                .setDepartmentId(dto.getDepartmentId())
                .setRole(dto.getRole())
                .setSalary(dto.getSalary())
                .setHireDate(new Date())
                .setOfficeLocation(dto.getOfficeLocation())
                .setStatus(OfficeEmployeeStatusEnum.PROBATION_PERIOD.getMark())
                .setGenre(dto.getGenre())
                .setCreateAt(new Date())
                .setModifiedAt(new Date())
        ;

        teamOfficeEmployeeMapper.insert(employee);

        return  employeeId;
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
