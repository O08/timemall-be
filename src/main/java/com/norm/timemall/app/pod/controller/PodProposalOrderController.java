package com.norm.timemall.app.pod.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.pod.domain.dto.PodGetProposalsPageDTO;
import com.norm.timemall.app.pod.domain.vo.PodGetProposalsPageRO;
import com.norm.timemall.app.pod.domain.vo.PodGetProposalsPageVO;
import com.norm.timemall.app.pod.service.PodProposalOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PodProposalOrderController {
    @Autowired
    private PodProposalOrderService podProposalOrderService;
    @GetMapping("/api/v1/web_pod/me/proposal/order/query")
    public PodGetProposalsPageVO fetchProposals(@Validated PodGetProposalsPageDTO dto){
        IPage<PodGetProposalsPageRO> proposal=podProposalOrderService.findProposal(dto);
        PodGetProposalsPageVO vo = new PodGetProposalsPageVO();
        vo.setProposal(proposal);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @PutMapping("/api/v1/web_pod/me/proposal/{id}/sign")
    public SuccessVO signProposal(@PathVariable("id") String id){

        podProposalOrderService.toSignProposal(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
