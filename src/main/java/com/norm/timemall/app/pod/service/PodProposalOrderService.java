package com.norm.timemall.app.pod.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.pod.domain.dto.PodGetProposalsPageDTO;
import com.norm.timemall.app.pod.domain.vo.PodGetProposalsPageRO;
import org.springframework.stereotype.Service;

@Service
public interface PodProposalOrderService {
    IPage<PodGetProposalsPageRO> findProposal(PodGetProposalsPageDTO dto);

    void toSignProposal(String id);
}
