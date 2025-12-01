package com.norm.timemall.app.team.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.mo.OfficeEmployeeMaterial;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.*;
import com.norm.timemall.app.team.domain.vo.*;
import com.norm.timemall.app.team.service.TeamOfficeEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.nio.file.InvalidPathException;
import java.nio.file.Paths;

@RestController
public class TeamOfficeEmployeeController {

    @Autowired
    private TeamOfficeEmployeeService teamOfficeEmployeeService;

    @Autowired
    private FileStoreService fileStoreService;

    @GetMapping("/api/v1/team/office/employee/query")
    public TeamOfficeQueryEmployeePageVO queryEmployees(@Validated TeamOfficeQueryEmployeePageDTO dto){
        IPage<TeamOfficeQueryEmployeePageRO> employee = teamOfficeEmployeeService.findEmployees(dto);
        TeamOfficeQueryEmployeePageVO vo = new TeamOfficeQueryEmployeePageVO();
        vo.setEmployee(employee);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @PostMapping("/api/v1/team/office/employee/create")
    public SuccessVO typeInEmployee(@Validated @RequestBody TeamOfficeTypeInEmployeeDTO dto){
        teamOfficeEmployeeService.addOneEmployee(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/team/office/employee/basic")
    public TeamOfficeFetchEmployeeBasicInfoVO fetchEmployeeBasicInfo(String id){
        TeamOfficeFetchEmployeeBasicInfoRO employee=teamOfficeEmployeeService.findEmployeeBasicInfo(id);
        TeamOfficeFetchEmployeeBasicInfoVO vo= new TeamOfficeFetchEmployeeBasicInfoVO();
        vo.setEmployee(employee);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/v1/team/office/employee/benefit/info")
    public TeamOfficeFetchEmployeeBenefitInfoVO fetchEmployeeBenefitInfo(String id){
        OfficeEmployeeBenefitRO benefit=teamOfficeEmployeeService.findEmployeeBenefitInfo(id);
        TeamOfficeFetchEmployeeBenefitInfoVO vo = new TeamOfficeFetchEmployeeBenefitInfoVO();
        vo.setBenefit(benefit);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @GetMapping("/api/v1/team/office/employee/compensation")
    public TeamOfficeFetchEmployeeCompensationInfoPageVO fetchEmployeeCompensationInfo(@Validated TeamOfficeFetchEmployeeCompensationInfoPageDTO dto){
        IPage<TeamOfficeFetchEmployeeCompensationInfoPageRO> compensation=teamOfficeEmployeeService.findEmployeeCompensationInfo(dto);
        TeamOfficeFetchEmployeeCompensationInfoPageVO vo = new TeamOfficeFetchEmployeeCompensationInfoPageVO();
        vo.setCompensation(compensation);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @GetMapping("/api/v1/team/office/employee/material/query")
    public TeamOfficeFetchEmployeeMaterialPageVO fetchEmployeeMaterial(@Validated TeamOfficeFetchEmployeeMaterialPageDTO dto){
        IPage<TeamOfficeFetchEmployeeMaterialPageRO> material=teamOfficeEmployeeService.findEmployeeMaterialInfo(dto);
        TeamOfficeFetchEmployeeMaterialPageVO vo = new TeamOfficeFetchEmployeeMaterialPageVO();
        vo.setMaterial(material);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @GetMapping("/api/v1/team/office/employee/kv")
    public TeamOfficeFetchEmployeeKvPageVO fetchEmployeeKv(@Validated TeamOfficeFetchEmployeeKvPageDTO dto){
        IPage<TeamOfficeFetchEmployeeKvPageRO> kv = teamOfficeEmployeeService.findEmployeeKvInfo(dto);
        TeamOfficeFetchEmployeeKvPageVO vo = new TeamOfficeFetchEmployeeKvPageVO();
        vo.setKv(kv);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @PutMapping("/api/v1/team/office/employee/basic/change")
    public  SuccessVO editEmployeeBasicInfo(@Validated @RequestBody TeamOfficeEditEmployeeBasicInfoDTO dto){
        teamOfficeEmployeeService.doEditEmployeeBasicInfo(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/team/office/employee/benefit/edit")
    public SuccessVO editEmployeeBenefitInfo(@Validated @RequestBody TeamOfficeEditEmployeeBenefitInfoDTO dto){
        teamOfficeEmployeeService.doEditEmployeeBenefitInfo(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @GetMapping("/api/v1/team/office/employee/compensation_config")
    public TeamOfficeFetchEmployeeCompensationConfigInfoPageVO fetchEmployeeCompensationConfigInfo(@Validated TeamOfficeFetchEmployeeCompensationConfigInfoPageDTO dto){
        IPage<TeamOfficeFetchEmployeeCompensationConfigInfoPageRO> compensationGrantInfo=teamOfficeEmployeeService.findEmployeeCompensationConfigInfo(dto);
        TeamOfficeFetchEmployeeCompensationConfigInfoPageVO vo = new TeamOfficeFetchEmployeeCompensationConfigInfoPageVO();
        vo.setCompensationGrantInfo(compensationGrantInfo);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @PutMapping("/api/v1/team/office/employee/compensation/config")
    public SuccessVO configEmployeeCompensation(@Validated @RequestBody TeamOfficeConfigEmployeeCompensationDTO dto){
        teamOfficeEmployeeService.doConfigEmployeeCompensation(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PostMapping("/api/v1/team/office/employee/material/upload")
    public SuccessVO uploadEmployeeMaterial(@Validated TeamOfficeUploadEmployeeMaterialDTO dto){
        // validate file
        if(dto.getMaterialFile() == null || dto.getMaterialFile().isEmpty()){
            throw new ErrorCodeException(CodeEnum.FILE_IS_EMPTY);
        }
        // validate role
        teamOfficeEmployeeService.validateCurrentUserIsEmployeeAdmin(dto.getEmployeeId());

        String materialName= dto.getMaterialFile().getOriginalFilename();
        // store file to oss
        String  materialUri = fileStoreService.storeWithLimitedAccess(dto.getMaterialFile(), FileStoreDir.PAYROLL_EMPLOYEE_MATERIAL);

        long fileSize = dto.getMaterialFile().getSize();
        teamOfficeEmployeeService.doSaveEmployeeMaterialInfo(dto.getEmployeeId(),materialName,materialUri,fileSize);


        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PostMapping("/api/v1/team/office/employee/kv/new")
    public SuccessVO createEmployeeKvPair(@Validated @RequestBody TeamOfficeCreateEmployeeKvPairDTO dto){
        // validate role
        teamOfficeEmployeeService.validateCurrentUserIsEmployeeAdmin(dto.getEmployeeId());
        teamOfficeEmployeeService.addEmployeeKvPair(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @DeleteMapping("/api/v1/team/office/employee/kv/{id}/del")
    public SuccessVO removeEmployeeKvPair(@PathVariable("id") String id){
        teamOfficeEmployeeService.doRemoveEmployeeKvPair(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }



    @DeleteMapping("/api/v1/team/office/empolyee/material/{id}/del")
    public SuccessVO removeEmployeeMaterial(@PathVariable("id") String id){
        OfficeEmployeeMaterial material=teamOfficeEmployeeService.findMaterialInfo(id);
        if(material==null){
            throw new QuickMessageException("未找到相关员工材料");
        }
        // validate role
        teamOfficeEmployeeService.validateCurrentUserIsEmployeeAdmin(material.getEmployeeId());
        // del from db
        teamOfficeEmployeeService.doRemoveEmployeeMaterial(id);
        // del from oss
        fileStoreService.deleteFile(material.getMaterialUri());

        return new SuccessVO(CodeEnum.SUCCESS);
    }

    @PutMapping("/api/v1/team/office/employee/material/remark")
    public SuccessVO remarkEmployeeMaterial(@Validated @RequestBody TeamOfficeRemarkEmployeeMaterialDTO dto){
        teamOfficeEmployeeService.remarkEmployeeMaterial(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @PutMapping("/api/v1/team/office/employee/material/rename")
    public SuccessVO renameEmployeeMaterial(@Validated @RequestBody TeamOfficeRenameEmployeeMaterialDTO dto){

        validateStringFilenameUsingNIO2(dto.getMaterialName());
        teamOfficeEmployeeService.renameEmployeeMaterial(dto);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    public static void validateStringFilenameUsingNIO2(String filename) {
        try {
            Paths.get(filename);
        }catch (InvalidPathException e){
            throw new QuickMessageException("文件名包含不支持字符");
        }
    }

}
