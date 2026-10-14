package com.norm.timemall.app.studio.service;

import com.norm.timemall.app.base.mo.ProposalMaterial;
import com.norm.timemall.app.studio.domain.dto.StudioGetProposalMaterialsDTO;
import com.norm.timemall.app.studio.domain.dto.StudioProposalMaterialRenameDTO;
import com.norm.timemall.app.studio.domain.dto.StudioProposalMaterialUploadDTO;
import com.norm.timemall.app.studio.domain.pojo.StudioGetProposalMaterialItem;
import org.springframework.stereotype.Service;

@Service
public interface StudioProposalMaterialService {
    StudioGetProposalMaterialItem[] findMaterials(StudioGetProposalMaterialsDTO dto);

    void newProposalMaterial(StudioProposalMaterialUploadDTO dto, String materialName, String materialUrl);

    ProposalMaterial findOneMaterial(String id);

    void delOneMaterial(String id);

    void rename(StudioProposalMaterialRenameDTO dto);

    void delMaterialsUsingProposalId(String proposalId);

}
