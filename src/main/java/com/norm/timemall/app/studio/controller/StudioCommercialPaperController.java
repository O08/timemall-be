package com.norm.timemall.app.studio.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.enums.CommercialPaperTagEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.base.helper.SecurityUserHelper;
import com.norm.timemall.app.base.mo.CommercialPaper;
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

import java.util.Date;

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
    @GetMapping("/api/public/commercial_paper/{id}/detail")
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

        // only support PUBLISH OFFLINE
        boolean validatedTag= CommercialPaperTagEnum.PUBLISH.getMark().equals(dto.getTag()) || CommercialPaperTagEnum.OFFLINE.getMark().equals(dto.getTag());
        if(!validatedTag){
            throw new QuickMessageException("暂不支持修订到目标状态");
        }
        CommercialPaper paper= studioApiAccessControlService.findPaper(dto.getPaperId());
        if(paper==null){
            throw new QuickMessageException("未找到相关商单");
        }


        boolean isFounder= paper.getPurchaser().equals(SecurityUserHelper.getCurrentPrincipal().getBrandId());
        if(!isFounder){
            throw new QuickMessageException("权限校验不通过");
        }
        // if paper is delivering or finish or closed,not support edit
        boolean notSupportEdit=CommercialPaperTagEnum.END.getMark().equals(paper.getTag())
                || CommercialPaperTagEnum.CLOSED.getMark().equals(paper.getTag())
                || CommercialPaperTagEnum.DELIVERING.getMark().equals(paper.getTag());

        if(notSupportEdit){
            throw new QuickMessageException("暂不支持修订交付中、已完成交付或中止状态的商单");
        }

        studioCommercialPaperService.modifyPaperTag(dto);

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
    @PutMapping("/api/v1/web_estudio/commercial_paper/{id}/redeploy_supplier")
    public SuccessVO redeploySupplier(@PathVariable("id") String id){
        CommercialPaper paper= studioApiAccessControlService.findPaper(id);
        if(paper==null){
            throw new QuickMessageException("未找到相关商单");
        }

        boolean isFounder= paper.getPurchaser().equals(SecurityUserHelper.getCurrentPrincipal().getBrandId());
        if(!isFounder){
            throw new QuickMessageException("权限校验不通过");
        }
        // only support paper that in delivering
        if(!CommercialPaperTagEnum.DELIVERING.getMark().equals(paper.getTag())){
            throw new QuickMessageException("仅支持处于交付状态的商单");
        }
        // paper order time in 24h
        DateTime endTime = DateUtil.offsetDay(paper.getBidAt(), 1);
        if(DateUtil.compare(endTime,new Date())<0){
            throw new QuickMessageException("已超出宽限期");
        }
        // business logic
        studioCommercialPaperService.emptySupplier(id);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

}
