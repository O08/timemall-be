package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.FileStoreDir;
import com.norm.timemall.app.base.enums.ProposalMaterialTypeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Proposal;
import com.norm.timemall.app.base.mo.ProposalMaterial;
import com.norm.timemall.app.base.service.FileStoreService;
import com.norm.timemall.app.studio.domain.dto.StudioGetProposalMaterialsDTO;
import com.norm.timemall.app.studio.domain.dto.StudioProposalMaterialRenameDTO;
import com.norm.timemall.app.studio.domain.dto.StudioProposalMaterialUploadDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioGetProposalMaterialItem;
import com.norm.timemall.app.studio.domain.vo.StudioGetProposalMaterialListVO;
import com.norm.timemall.app.studio.service.StudioProposalMaterialService;
import com.norm.timemall.app.studio.service.StudioProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioProposalMaterialController {

    @Autowired
    private FileStoreService fileStoreService;

    @Autowired
    private StudioProposalMaterialService studioProposalMaterialService;

    @Autowired
    private StudioProposalService studioProposalService;

    @GetMapping("/api/v1/web_estudio/brand/proposal/material/query")
    public StudioGetProposalMaterialListVO getProposalMaterials(@Validated StudioGetProposalMaterialsDTO dto){

        StudioGetProposalMaterialItem[] materials = studioProposalMaterialService.findMaterials(dto);
        StudioGetProposalMaterialListVO vo = new StudioGetProposalMaterialListVO();
        vo.setMaterials(materials);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @PostMapping("/api/v1/web_estudio/brand/proposal/material/upload")
    public SuccessVO uploadMaterial(@Validated StudioProposalMaterialUploadDTO dto){

        // validate file
        if(dto.getMaterial() == null || dto.getMaterial().isEmpty()){
            throw new QuickMessageException("资料文件未上传或者为空文件");
        }

        Proposal proposal= studioProposalService.findOneProposalUsingId(dto.getProposalId());
        if(proposal==null){
            throw new QuickMessageException("未找到相关提案");
        }
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentBrandId.equals(proposal.getSellerBrandId());
        boolean isBuyer=currentBrandId.equals(proposal.getBuyerBrandId());

        // buyer only support upload buyer material
        if(isBuyer && !ProposalMaterialTypeEnum.BUYER.getMark().equals(dto.getMaterialType())){
            throw new QuickMessageException("买家仅支持上传买家资料");
        }
        // seller only support seller and deliver
        if(isSeller && ProposalMaterialTypeEnum.BUYER.getMark().equals(dto.getMaterialType())){
            throw new QuickMessageException("未拥有上传买家资料权限");
        }
        // block access for illegal user
        if(!(isBuyer || isSeller)){
             throw new QuickMessageException("非法访问");
        }

        // upload file
        String materialUrl = fileStoreService.storeWithLimitedAccess(dto.getMaterial(), FileStoreDir.PROPOSAL_MATERIAL);

        String materialName = dto.getMaterial().getOriginalFilename();

        studioProposalMaterialService.newProposalMaterial(dto,materialName,materialUrl);

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @DeleteMapping("/api/v1/web_estudio/brand/proposal/material/{id}/del")
    public SuccessVO delMaterial(@PathVariable("id") String id){
        ProposalMaterial material =  studioProposalMaterialService.findOneMaterial(id);
        if(material==null){
            throw new QuickMessageException("未找到相关提案材料");
        }
        Proposal proposal= studioProposalService.findOneProposalUsingId(material.getProposalId());
        if(proposal==null){
            throw new QuickMessageException("未找到相关提案");
        }
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentBrandId.equals(proposal.getSellerBrandId());
        boolean isBuyer=currentBrandId.equals(proposal.getBuyerBrandId());

        // buyer only support del buyer material
        if(isBuyer && !ProposalMaterialTypeEnum.BUYER.getMark().equals(material.getMaterialType())){
            throw new QuickMessageException("权限验证失败");
        }
        // seller only support del seller and deliver
        if(isSeller && ProposalMaterialTypeEnum.BUYER.getMark().equals(material.getMaterialType())){
            throw new QuickMessageException("权限验证失败");
        }
        // block access for illegal user
        if(!(isBuyer || isSeller)){
            throw new QuickMessageException("非法访问");
        }
        // del from db
        studioProposalMaterialService.delOneMaterial(id);
        // del from oss
        fileStoreService.deleteFile(material.getMaterialUrl());

        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/web_estudio/brand/proposal/material/rename")
    public SuccessVO renameMaterial(@Validated @RequestBody StudioProposalMaterialRenameDTO dto){

        studioProposalMaterialService.rename(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
