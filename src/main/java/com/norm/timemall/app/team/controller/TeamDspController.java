package com.norm.timemall.app.team.controller;

import cn.hutool.core.lang.Validator;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.pojo.BaseFetchCodeMapping;
import com.norm.timemall.app.base.pojo.dto.BaseFetchCodeMappingDTO;
import com.norm.timemall.app.base.service.BaseCodeMappingService;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.ro.TeamFetchDspCaseLibraryPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchDspCaseListPageRO;
import com.norm.timemall.app.team.domain.vo.TeamFetchDspCaseLibraryPageVO;
import com.norm.timemall.app.team.domain.vo.TeamFetchDspCaseListPageVO;
import com.norm.timemall.app.team.domain.vo.TeamGetDspCaseMaterialVO;
import com.norm.timemall.app.team.domain.vo.TeamGetDspOneCaseInfoVO;
import com.norm.timemall.app.team.service.TeamDspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamDspController {
    @Autowired
    private TeamDspService teamDspService;

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private BaseCodeMappingService baseCodeMappingService;

    @PostMapping("/api/v1/team/dsp_case/new")
    public SuccessVO addCase(@Validated TeamDspAddCaseDTO dto){
        String materialUrl="";
        String materialName="";
        // validate link url
        if(!Validator.isUrl(dto.getSceneUrl())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // validate fraud type
        BaseFetchCodeMappingDTO codeMappingDTO = new BaseFetchCodeMappingDTO();
        codeMappingDTO.setCodeType("fraud_type");
        codeMappingDTO.setItemCode(dto.getFraudType());
        BaseFetchCodeMapping codeMappingList = baseCodeMappingService.findCodeMappingList(codeMappingDTO);
        if(codeMappingList.getRecords().isEmpty()){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        // store file to oss
        if(!(dto.getMaterial() == null || dto.getMaterial().isEmpty())){
            materialName = dto.getMaterial().getOriginalFilename();
            materialUrl = fileStoreService.storeWithLimitedAccess(dto.getMaterial(), FileStoreDir.CASE_MATERIAL);
        }



        teamDspService.newCase(dto,materialName,materialUrl);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PostMapping("/api/v1/team/dsp_case/add_material")
    public SuccessVO addCaseMaterial(@Validated TeamDspAddCaseMaterialDTO dto){
        // validate file
        if(dto.getMaterial() == null || dto.getMaterial().isEmpty()){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        String materialUrl = fileStoreService.storeWithLimitedAccess(dto.getMaterial(), FileStoreDir.CASE_MATERIAL);

        String materialName = dto.getMaterial().getOriginalFilename();

        teamDspService.newCaseMaterial(dto,materialName,materialUrl);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/team/dsp_case/change")
    public SuccessVO changeDspCase(@Validated @RequestBody TeamDspCaseChangeDTO dto){

        teamDspService.modifyCase(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/team/dsp_case/action/cell/{id}/offline")
    public SuccessVO offlineCell(@PathVariable("id") String id){

        teamDspService.doOfflineCell(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/team/dsp_case/action/withdraw_limits")
    public SuccessVO withdrawToAlipayCreditSetting(@Validated @RequestBody WithdrawToAlipayCreditSettingDTO dto){

        teamDspService.withdrawToAlipayCreditSetting(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }


    @GetMapping("/api/v1/team/dsp_case/list")
    public TeamFetchDspCaseListPageVO fetchDspCaseList(@Validated TeamFetchDspCaseListPageDTO dto){

        IPage<TeamFetchDspCaseListPageRO> dspCaseList = teamDspService.findDspCaseList(dto);
        TeamFetchDspCaseListPageVO vo = new TeamFetchDspCaseListPageVO();
        vo.setReportCase(dspCaseList);
        vo.setResponseCode(CodeEnum.SUCCESS);

        return vo;

    }

    @GetMapping("/api/v1/team/dsp_case/library")
    public TeamFetchDspCaseLibraryPageVO fetchDspCaseLibrary(@Validated TeamFetchDspCaseLibraryPageDTO dto){

        IPage<TeamFetchDspCaseLibraryPageRO> dspCaseLibrary = teamDspService.findDspCaseLibrary(dto);
        TeamFetchDspCaseLibraryPageVO vo = new TeamFetchDspCaseLibraryPageVO();
        vo.setReportCase(dspCaseLibrary);
        vo.setResponseCode(CodeEnum.SUCCESS);

        return vo;

    }

    @GetMapping("/api/v1/team/dsp_case/{case_no}/info")
    public TeamGetDspOneCaseInfoVO getDspOneCaseInfo(@PathVariable("case_no") String caseNO){

        return teamDspService.findDspOneCaseInfo(caseNO);

    }

    @GetMapping("/api/v1/team/dsp_case/{case_no}/material")
    public TeamGetDspCaseMaterialVO getCaseMaterial(@PathVariable("case_no") String caseNO,String materialType){

        return teamDspService.findDspCaseMaterial(caseNO,materialType);

    }



}
