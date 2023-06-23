package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.*;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaper;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsPaperDetail;
import com.norm.timemall.app.studio.domain.ro.StudioDiscoverMpsPaperPageRO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsPaperRO;
import com.norm.timemall.app.studio.domain.vo.StudioDiscoverMpsPaperPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchMpsPaperDetailVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchMpsPaperPageVO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchMpsPaperVO;
import com.norm.timemall.app.studio.service.StudioApiAccessControlService;
import com.norm.timemall.app.studio.service.StudioCommercialPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioCommercialPaperController {
    @Autowired
    private StudioCommercialPaperService studioCommercialPaperService;
    @Autowired
    private StudioApiAccessControlService studioApiAccessControlService;

    @GetMapping("/api/v1/web_estudio/brand/commercial_paper")
    public StudioFetchMpsPaperPageVO fetchMpsPaperPageForBrand(StudioFetchMpsPaperPageDTO dto){
        IPage<StudioFetchMpsPaperRO> paper = studioCommercialPaperService.findPaperPageForBrand(dto);
        StudioFetchMpsPaperPageVO vo = new StudioFetchMpsPaperPageVO();
        vo.setPaper(paper);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/v1/web_estudio/discover/commercial_paper")
    public StudioDiscoverMpsPaperPageVO discoverMpsPaperPage(StudioDiscoverMpsPaperPageDTO dto){

        IPage<StudioDiscoverMpsPaperPageRO> paper = studioCommercialPaperService.discoverMpsPaper(dto);
        StudioDiscoverMpsPaperPageVO vo = new StudioDiscoverMpsPaperPageVO();
        vo.setPaper(paper);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @GetMapping("/api/v1/web_estudio/commercial_paper/{id}/detail")
    public StudioFetchMpsPaperDetailVO fetchMpsPaperDetail(@PathVariable("id")String id){
        StudioFetchMpsPaperDetail detail = studioCommercialPaperService.findMpsPaperDetail(id);
        StudioFetchMpsPaperDetailVO vo = new StudioFetchMpsPaperDetailVO();
        vo.setDetail(detail);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @GetMapping("/api/v1/web_estudio/mps/paper")
    public StudioFetchMpsPaperVO fetchMpsPaperList( @Validated StudioFetchmpsPaperListDTO dto){

        StudioFetchMpsPaper paper = studioCommercialPaperService.findPaperList(dto);
        StudioFetchMpsPaperVO vo = new StudioFetchMpsPaperVO();
        vo.setPaper(paper);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return  vo;

    }
    @PutMapping("/api/v1/web_estudio/mps_paper/tag")
    public SuccessVO putMpsPaperTag(@RequestBody @Validated StudioPutMpsPaperTagDTO dto){

        boolean isFounder= studioApiAccessControlService.isMpscPaperFounder(dto.getPaperId());
        if(isFounder){
            studioCommercialPaperService.modifyPaperTag(dto);
        }
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @PutMapping("/api/v1/web_estudio/brand/mps_paper")
    public SuccessVO putMpsPaper(@RequestBody  @Validated StudioPutMpsPaperDTO dto){
        boolean isFounder= studioApiAccessControlService.isMpscPaperFounder(dto.getPaperId());
        if(isFounder){
            studioCommercialPaperService.modifyPaper(dto);
        }
        return new SuccessVO(CodeEnum.SUCCESS);

    }

}
