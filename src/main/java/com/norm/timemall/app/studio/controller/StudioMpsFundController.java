package com.norm.timemall.app.studio.controller;

import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.studio.domain.pojo.StudioFetchMpsFund;
import com.norm.timemall.app.studio.domain.vo.StudioFetchMpsFundVO;
import com.norm.timemall.app.studio.service.StudioMpsFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudioMpsFundController {
    @Autowired
    private StudioMpsFundService studioMpsFundService;

    @ResponseBody
    @GetMapping(value = "/api/v1/web_estudio/brand/mps/fund")
    public StudioFetchMpsFundVO fetchMpsFundForBrand(){

        StudioFetchMpsFund fund = studioMpsFundService.getMpsFundForBrand();
        StudioFetchMpsFundVO vo = new StudioFetchMpsFundVO();
        vo.setFund(fund);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }


}
