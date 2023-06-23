package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.mo.Mps;
import com.norm.timemall.app.studio.domain.dto.StudioFetchMpsListPageDTO;
import com.norm.timemall.app.studio.domain.dto.StudioNewMpsDTO;
import com.norm.timemall.app.studio.domain.ro.StudioFetchMpsListRO;
import com.norm.timemall.app.studio.domain.vo.StudioFetchMpsListPageVO;
import com.norm.timemall.app.studio.service.StudioCommercialPaperService;
import com.norm.timemall.app.studio.service.StudioMpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudioMpsController {
    @Autowired
    private StudioMpsService studioMpsService;
    @Autowired
    private StudioCommercialPaperService studioCommercialPaperService;
    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/mps")
    public StudioFetchMpsListPageVO fetchMpsList(StudioFetchMpsListPageDTO dto){
        IPage<StudioFetchMpsListRO> mps= studioMpsService.fetchMpsList(dto);
        StudioFetchMpsListPageVO vo = new StudioFetchMpsListPageVO();
        vo.setMps(mps);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }
    @ResponseBody
    @PostMapping(value = "/api/v1/web_estudio/brand/mps/new")
    public SuccessVO newMps(@RequestBody @Validated StudioNewMpsDTO dto){
        // new mps
        Mps mps = studioMpsService.newMps(dto);
        // generate mps paper
        studioCommercialPaperService.generateMpsPaper(mps.getChainId(),mps.getBrandId(), mps.getId());
        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
