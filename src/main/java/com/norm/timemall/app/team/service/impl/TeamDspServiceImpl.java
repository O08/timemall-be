package com.norm.timemall.app.team.service.impl;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.norm.timemall.app.base.enums.*;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mapper.BaseSequenceMapper;
import com.norm.timemall.app.base.mo.*;
import com.norm.timemall.app.team.domain.dto.*;
import com.norm.timemall.app.team.domain.pojo.TeamDspCaseMaterial;
import com.norm.timemall.app.team.domain.ro.TeamFetchDspCaseLibraryPageRO;
import com.norm.timemall.app.team.domain.ro.TeamFetchDspCaseListPageRO;
import com.norm.timemall.app.team.domain.ro.TeamGetDspOneCaseInfoBasicRO;
import com.norm.timemall.app.team.domain.ro.TeamGetDspOneCaseInfoMaterialRO;
import com.norm.timemall.app.team.domain.vo.TeamGetDspCaseMaterialVO;
import com.norm.timemall.app.team.domain.vo.TeamGetDspOneCaseInfoVO;
import com.norm.timemall.app.team.mapper.*;
import com.norm.timemall.app.team.service.TeamDspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Date;
@Service
public class TeamDspServiceImpl implements TeamDspService {
    @Autowired
    private TeamDspCaseMapper teamDspCaseMapper;

    @Autowired
    private TeamDspCaseMaterialMapper teamDspCaseMaterialMapper;

    @Autowired
    private BaseSequenceMapper baseSequenceMapper;

    @Autowired
    private TeamCellMapper teamCellMapper;
    @Autowired
    private TeamOasisMapper teamOasisMapper;

    @Autowired
    private TeamBluvarrierMapper teamBluvarrierMapper;

    @Autowired
    private TeamFinAccountMapper teamFinAccountMapper;

    @Override
    public void newCase(TeamDspAddCaseDTO dto,String materialName,String materialUrl) {
        String defendantBrandId = getDefendantBrandId(dto.getScene(),dto.getSceneUrl());
        if(CharSequenceUtil.isBlank(defendantBrandId)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        LambdaQueryWrapper<Bluvarrier> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark());
        Bluvarrier peacemaker = teamBluvarrierMapper.selectOne(lambdaQueryWrapper);

        Long no = baseSequenceMapper.nextSequence(SequenceKeyEnum.DSP_CASE_NO.getMark());
        String caseNO = "FO"+RandomUtil.randomStringUpper(5)+no;
        DspCase dspCase = new DspCase();
        dspCase.setId(IdUtil.simpleUUID())
                .setCaseNo(caseNO)
                .setFraudType(dto.getFraudType())
                .setCaseDesc(dto.getCaseDesc())
                .setScene(dto.getScene())
                .setSceneUrl(dto.getSceneUrl())
                .setPeacemakerBrandId(peacemaker.getBrandId())
                .setInformerBrandId(SecurityUserHelper.getCurrentPrincipal().getBrandId())
                .setDefendantBrandId(defendantBrandId)
                .setCasePriority(dto.getFraudType().substring(dto.getFraudType().length() - 1))
                .setCaseStatus(DspCaseStatusEnum.PENDING.name())
                .setCreateAt(new Date())
                .setModifiedAt(new Date())
        ;

        teamDspCaseMapper.insert(dspCase);

        DspCaseMaterial material = new DspCaseMaterial();
        material.setId(IdUtil.simpleUUID())
                .setMaterialName(materialName)
                .setMaterialUrl(materialUrl)
                .setCaseNo(caseNO)
                .setMaterialType(DspMaterialTypeEnum.INFORMER.getMark())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamDspCaseMaterialMapper.insert(material);


    }

    @Override
    public IPage<TeamFetchDspCaseListPageRO>  findDspCaseList(TeamFetchDspCaseListPageDTO dto) {

        IPage<TeamFetchDspCaseListPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();

        return teamDspCaseMapper.selectPageByQ(page,dto,currentBrandId);

    }

    @Override
    public IPage<TeamFetchDspCaseLibraryPageRO>  findDspCaseLibrary(TeamFetchDspCaseLibraryPageDTO dto) {

        IPage<TeamFetchDspCaseLibraryPageRO> page = new Page<>();
        page.setSize(dto.getSize());
        page.setCurrent(dto.getCurrent());
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        return teamDspCaseMapper.selectLibraryByQ(page,dto,currentBrandId);

    }

    @Override
    public TeamGetDspOneCaseInfoVO findDspOneCaseInfo(String caseNO) {

        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        TeamGetDspOneCaseInfoBasicRO general= teamDspCaseMapper.selectOneCaseByCaseNO(caseNO,currentBrandId);
        if(general==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        TeamDspCaseMaterial[] informerMaterial=teamDspCaseMaterialMapper.selectListByCaseNoAndType(caseNO,DspMaterialTypeEnum.INFORMER.getMark());
        TeamDspCaseMaterial[] defendantMaterial=teamDspCaseMaterialMapper.selectListByCaseNoAndType(caseNO,DspMaterialTypeEnum.DEFENDANT.getMark());
        TeamDspCaseMaterial[] peacemakerMaterial=teamDspCaseMaterialMapper.selectListByCaseNoAndType(caseNO,DspMaterialTypeEnum.PEACEMAKER.getMark());

        TeamGetDspOneCaseInfoMaterialRO material= new TeamGetDspOneCaseInfoMaterialRO();
        material.setDefendantMaterial(defendantMaterial);
        material.setPeacemakerMaterial(peacemakerMaterial);
        material.setInformerMaterial(informerMaterial);

        TeamGetDspOneCaseInfoVO vo= new TeamGetDspOneCaseInfoVO();
        vo.setGeneral(general);
        vo.setMaterial(material);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @Override
    public TeamGetDspCaseMaterialVO findDspCaseMaterial(String caseNO, String materialType) {

        if(CharSequenceUtil.isBlank(materialType)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<DspCase> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(DspCase::getCaseNo,caseNO);
        DspCase dspCase = teamDspCaseMapper.selectOne(wrapper);

        if(dspCase==null ){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        boolean informerValidated=materialType.equals(DspMaterialTypeEnum.INFORMER.getMark()) && currentBrandId.equals(dspCase.getInformerBrandId());
        boolean defendantValidated=materialType.equals(DspMaterialTypeEnum.DEFENDANT.getMark()) && currentBrandId.equals(dspCase.getDefendantBrandId());
        if(!( informerValidated || defendantValidated)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        TeamDspCaseMaterial[] material=teamDspCaseMaterialMapper.selectListByCaseNoAndType(caseNO,materialType);

        TeamGetDspCaseMaterialVO vo = new TeamGetDspCaseMaterialVO();
        vo.setMaterial(material);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @Override
    public void newCaseMaterial(TeamDspAddCaseMaterialDTO dto, String materialName, String materialUrl) {

        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<DspCase> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(DspCase::getCaseNo,dto.getCaseNO());
        DspCase dspCase = teamDspCaseMapper.selectOne(wrapper);

        if(dspCase==null){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        // validated data permission
        boolean informerValidated=dto.getMaterialType().equals(DspMaterialTypeEnum.INFORMER.getMark()) && currentBrandId.equals(dspCase.getInformerBrandId());
        boolean defendantValidated=dto.getMaterialType().equals(DspMaterialTypeEnum.DEFENDANT.getMark()) && currentBrandId.equals(dspCase.getDefendantBrandId());
        boolean peacemakerValidated=dto.getMaterialType().equals(DspMaterialTypeEnum.PEACEMAKER.getMark()) && currentBrandId.equals(dspCase.getPeacemakerBrandId());
        if(!( informerValidated || defendantValidated || peacemakerValidated)){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }

        DspCaseMaterial material = new DspCaseMaterial();
        material.setId(IdUtil.simpleUUID())
                .setCaseNo(dto.getCaseNO())
                .setMaterialName(materialName)
                .setMaterialUrl(materialUrl)
                .setMaterialType(dto.getMaterialType())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        teamDspCaseMaterialMapper.insert(material);

    }

    @Override
    public void modifyCase(TeamDspCaseChangeDTO dto) {

        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();

        LambdaQueryWrapper<DspCase> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(DspCase::getCaseNo,dto.getCaseNo());

        DspCase dspCase = teamDspCaseMapper.selectOne(wrapper);
        if(dspCase==null || !currentBrandId.equals(dspCase.getPeacemakerBrandId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }


        teamDspCaseMapper.updateCaseInfoByCaseNO(dto);

    }

    @Override
    public void doOfflineCell(String id) {


        validatedRoleAsPeacemaker();

        // validated cell
        Cell targetCell = teamCellMapper.selectById(id);
        if(targetCell==null){
            throw new QuickMessageException("cell not exist");
        }

        Cell cell =new Cell();
        cell.setId(id)
                .setMark(CellMarkEnum.OFFLINE.getMark())
                .setModifiedAt(new Date());
        teamCellMapper.updateById(cell);

    }

    private void validatedRoleAsPeacemaker() {
        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<Bluvarrier> lambdaQueryWrapper= Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(Bluvarrier::getRoleCode, BluvarrierRoleEnum.HOSTING.getMark());
        Bluvarrier bluvarrier = teamBluvarrierMapper.selectOne(lambdaQueryWrapper);
        if(bluvarrier==null || !currentBrandId.equals(bluvarrier.getBrandId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
    }

    @Override
    public void withdrawToAlipayCreditSetting(WithdrawToAlipayCreditSettingDTO dto) {

        String currentBrandId=SecurityUserHelper.getCurrentPrincipal().getBrandId();
        LambdaQueryWrapper<DspCase> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(DspCase::getCaseNo,dto.getCaseNO());

        DspCase dspCase = teamDspCaseMapper.selectOne(wrapper);
        if(dspCase==null || !currentBrandId.equals(dspCase.getPeacemakerBrandId())){
            throw new ErrorCodeException(CodeEnum.INVALID_PARAMETERS);
        }
        FinAccount brandFinAccount = new FinAccount();
        brandFinAccount.setFid(dspCase.getDefendantBrandId())
                .setFidType(FidTypeEnum.BRAND.getMark())
                .setMark(dto.getOffOrOn())
                .setBankLine(BigDecimal.valueOf(500));

        LambdaQueryWrapper<FinAccount> defendantFinAccountWrapper=Wrappers.lambdaQuery();
        defendantFinAccountWrapper.eq(FinAccount::getFid,dspCase.getDefendantBrandId())
                .eq(FinAccount::getFidType,FidTypeEnum.BRAND.getMark());


        teamFinAccountMapper.update(brandFinAccount,defendantFinAccountWrapper);

    }

    private String getDefendantBrandId(String scene,String sceneUrl){
        String defendantBrandId="";
        switch(scene){
            case "线上商品":

                String cellId = HttpUtil.decodeParams(sceneUrl, StandardCharsets.UTF_8).get("cell_id").getFirst();
                defendantBrandId = getDefendantBrandIdByCellId(cellId);

                break;

            case "彩虹部落":
                String oasisId = HttpUtil.decodeParams(sceneUrl, StandardCharsets.UTF_8).get("oasis_id").getFirst();
                defendantBrandId=getDefendantBrandIdByOasisId(oasisId);

                break;
            default:
                break;
        }

        return defendantBrandId;
    }

    private String getDefendantBrandIdByCellId(String cellId){
        Cell cell = teamCellMapper.selectById(cellId);
        return cell == null ? "" : cell.getBrandId();

    }
    private String getDefendantBrandIdByOasisId(String oasisId){

        Oasis oasis = teamOasisMapper.selectById(oasisId);
        return  oasis == null ? "" : oasis.getInitiatorId();

    }
}
