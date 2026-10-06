package com.norm.timemall.app.studio.service.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.norm.timemall.app.base.enums.ProposalMaterialTypeEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.Proposal;
import com.norm.timemall.app.base.mo.ProposalMaterial;
import com.norm.timemall.app.studio.domain.dto.StudioGetProposalMaterialsDTO;
import com.norm.timemall.app.studio.domain.dto.StudioProposalMaterialRenameDTO;
import com.norm.timemall.app.studio.domain.dto.StudioProposalMaterialUploadDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioGetProposalMaterialItem;
import com.norm.timemall.app.studio.mapper.StudioProposalMapper;
import com.norm.timemall.app.studio.mapper.StudioProposalMaterialMapper;
import com.norm.timemall.app.studio.service.StudioProposalMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class StudioProposalMaterialServiceImpl implements StudioProposalMaterialService {
    @Autowired
    private StudioProposalMaterialMapper studioProposalMaterialMapper;

    @Autowired
    private StudioProposalMapper studioProposalMapper;
    @Override
    public StudioGetProposalMaterialItem[] findMaterials(StudioGetProposalMaterialsDTO dto) {

        return studioProposalMaterialMapper.selectListByProposalIdAndType(dto.getProposalId(),dto.getMaterialType());

    }

    @Override
    public void newProposalMaterial(StudioProposalMaterialUploadDTO dto, String materialName, String materialUrl) {

        ProposalMaterial material = new ProposalMaterial();
        material.setId(IdUtil.simpleUUID())
                .setMaterialName(materialName)
                .setMaterialUrl(materialUrl)
                .setProposalId(dto.getProposalId())
                .setMaterialType(dto.getMaterialType())
                .setCreateAt(new Date())
                .setModifiedAt(new Date());

        studioProposalMaterialMapper.insert(material);

    }

    @Override
    public ProposalMaterial findOneMaterial(String id) {
        return studioProposalMaterialMapper.selectById(id);
    }

    @Override
    public void delOneMaterial(String id) {
        studioProposalMaterialMapper.deleteById(id);
    }

    @Override
    public void rename(StudioProposalMaterialRenameDTO dto) {
        ProposalMaterial material = studioProposalMaterialMapper.selectById(dto.getId());
        if(material==null){
            throw new QuickMessageException("未找到相关提案材料");
        }
        Proposal proposal = studioProposalMapper.selectById(material.getProposalId());
        if(proposal==null){
            throw new QuickMessageException("未找到相关提案");
        }
        String currentBrandId= SecurityUserHelper.getCurrentPrincipal().getBrandId();
        boolean isSeller= currentBrandId.equals(proposal.getSellerBrandId());
        boolean isBuyer=currentBrandId.equals(proposal.getBuyerBrandId());

        // buyer only support upload buyer material
        if(isBuyer && !ProposalMaterialTypeEnum.BUYER.getMark().equals(material.getMaterialType())){
            throw new QuickMessageException("权限验证失败");
        }
        // seller only support seller and deliver
        if(isSeller && ProposalMaterialTypeEnum.BUYER.getMark().equals(material.getMaterialType())){
            throw new QuickMessageException("权限验证失败");
        }
        // block access for illegal user
        if(!(isBuyer || isSeller)){
            throw new QuickMessageException("非法访问");
        }
        material.setMaterialName(dto.getMaterialName());
        material.setModifiedAt(new Date());
        studioProposalMaterialMapper.updateById(material);

    }

    @Override
    public void delMaterialsUsingProposalId(String proposalId) {
        LambdaQueryWrapper<ProposalMaterial> wrapper= Wrappers.lambdaQuery();
        wrapper.eq(ProposalMaterial::getProposalId,proposalId);
        studioProposalMaterialMapper.delete(wrapper);
    }
}
