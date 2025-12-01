package com.norm.timemall.app.team.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.OfficeEmployeeMaterial;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.*;
import org.springframework.stereotype.Service;

@Service
public interface TeamOfficeEmployeeService {
    IPage<TeamOfficeQueryEmployeePageRO> findEmployees(TeamOfficeQueryEmployeePageDTO dto);

    void addOneEmployee(TeamOfficeTypeInEmployeeDTO dto);

    TeamOfficeFetchEmployeeBasicInfoRO findEmployeeBasicInfo(String id);

    OfficeEmployeeBenefitRO findEmployeeBenefitInfo(String id);

    IPage<TeamOfficeFetchEmployeeCompensationInfoPageRO> findEmployeeCompensationInfo(TeamOfficeFetchEmployeeCompensationInfoPageDTO dto);

    IPage<TeamOfficeFetchEmployeeMaterialPageRO> findEmployeeMaterialInfo(TeamOfficeFetchEmployeeMaterialPageDTO dto);

    IPage<TeamOfficeFetchEmployeeKvPageRO> findEmployeeKvInfo(TeamOfficeFetchEmployeeKvPageDTO dto);

    void doEditEmployeeBasicInfo(TeamOfficeEditEmployeeBasicInfoDTO dto);

    void doEditEmployeeBenefitInfo(TeamOfficeEditEmployeeBenefitInfoDTO dto);

    IPage<TeamOfficeFetchEmployeeCompensationConfigInfoPageRO> findEmployeeCompensationConfigInfo(TeamOfficeFetchEmployeeCompensationConfigInfoPageDTO dto);

    void doConfigEmployeeCompensation(TeamOfficeConfigEmployeeCompensationDTO dto);

    void doSaveEmployeeMaterialInfo(String employeeId, String materialName, String materialUri,Long size);

    void validateCurrentUserIsEmployeeAdmin(String employeeId);

    void addEmployeeKvPair(TeamOfficeCreateEmployeeKvPairDTO dto);

    void doRemoveEmployeeKvPair(String id);

    OfficeEmployeeMaterial findMaterialInfo(String id);

    void doRemoveEmployeeMaterial(String id);

    void remarkEmployeeMaterial(TeamOfficeRemarkEmployeeMaterialDTO dto);

    void renameEmployeeMaterial(TeamOfficeRenameEmployeeMaterialDTO dto);
}
