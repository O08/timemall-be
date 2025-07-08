package com.norm.timemall.app.studio.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.mo.Proposal;
import com.norm.timemall.app.studio.domain.dto.StudioChangeProposalDTO;
import com.norm.timemall.app.studio.domain.dto.StudioChangeProposalStatusDTO;
import com.norm.timemall.app.studio.domain.dto.StudioCreateNewProposalDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchProposalPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchProposalPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchOneProposalVO;
import org.springframework.stereotype.Service;

@Service
public interface StudioProposalService {
    void newOneProposal(StudioCreateNewProposalDTO dto);

    void modifyProposal(StudioChangeProposalDTO dto);

    IPage<StudioFetchProposalPageRO> findProposals(StudioFetchProposalPageDTO dto);

    void duplicateOneProposal(String id);

    void modifyProjectStatus(StudioChangeProposalStatusDTO dto);

    void delOneProposal(String id);

    StudioFetchOneProposalVO findOneProposal(String no);

    Proposal findOneProposalUsingId(String proposalId);
}
