package com.norm.timemall.app.studio.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.dto.FetchSemiDataPageDTO;
import com.norm.timemall.app.studio.domain.ro.FetchSemiDataRO;
import com.norm.timemall.app.studio.domain.vo.FetchSemiDataPageVO;
import com.norm.timemall.app.studio.service.StudioDataScienceSemiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenDataController {

    @Autowired
    private StudioDataScienceSemiService studioDataScienceSemiService;

    @GetMapping("/api/v1/web_studio/open/data/semi")
    public FetchSemiDataPageVO fetchSemiData(@Validated FetchSemiDataPageDTO dto){

        IPage<FetchSemiDataRO> semi = studioDataScienceSemiService.findSemiDataPage(dto);
        FetchSemiDataPageVO vo = new FetchSemiDataPageVO();
        vo.setSemi(semi);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
}
