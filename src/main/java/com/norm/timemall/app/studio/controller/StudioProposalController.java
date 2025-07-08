package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.StudioChangeProposalDTO;
import com.norm.timemall.app.studio.domain.dto.StudioChangeProposalStatusDTO;
import com.norm.timemall.app.studio.domain.dto.StudioCreateNewProposalDTO;
import com.norm.timemall.app.studio.domain.dto.StudioFetchProposalPageDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchProposalPageRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchOneProposalVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchProposalPageVO;
import com.norm.timemall.app.studio.service.StudioProposalMaterialService;
import com.norm.timemall.app.studio.service.StudioProposalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioProposalController {
    @Autowired
    private StudioProposalService studioProposalService;

    @Autowired
    private StudioProposalMaterialService studioProposalMaterialService;

    @GetMapping("/api/v1/web_estudio/brand/proposal/query")
    public StudioFetchProposalPageVO fetchProposal(@Validated StudioFetchProposalPageDTO dto){

        IPage<StudioFetchProposalPageRO> proposal=studioProposalService.findProposals(dto);
        StudioFetchProposalPageVO vo = new StudioFetchProposalPageVO();
        vo.setProposal(proposal);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }

    @PostMapping("/api/v1/web_estudio/brand/proposal/new")
    public SuccessVO createNewProposal(@Validated @RequestBody StudioCreateNewProposalDTO dto){

        studioProposalService.newOneProposal(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_estudio/brand/proposal/change")
    public SuccessVO changeProposal(@Validated @RequestBody StudioChangeProposalDTO dto){

        studioProposalService.modifyProposal(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PostMapping("/api/v1/web_estudio/brand/proposal/{id}/duplicate")
    public SuccessVO duplicateProposal(@PathVariable("id") String id){

        studioProposalService.duplicateOneProposal(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

    @PutMapping("/api/v1/web_estudio/brand/proposal/change_project_status")
    public SuccessVO changeProjectStatus(@Validated @RequestBody StudioChangeProposalStatusDTO dto){

        studioProposalService.modifyProjectStatus(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @DeleteMapping("/api/v1/web_estudio/brand/proposal/{id}/del")
    public SuccessVO delProposal(@PathVariable("id") String id){

        studioProposalService.delOneProposal(id);
        studioProposalMaterialService.delMaterialsUsingProposalId(id);
        return new SuccessVO(CodeEnum.SUCCESS);
    }
    @GetMapping("/api/v1/web_estudio/brand/proposal/{no}/query")
    public StudioFetchOneProposalVO fetchOneProposal(@PathVariable("no") String no){

        return  studioProposalService.findOneProposal(no);

    }

}
