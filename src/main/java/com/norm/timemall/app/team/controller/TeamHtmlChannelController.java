package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.EditHtmlContentDTO;
import com.norm.timemall.app.team.domain.vo.FetchHtmlChannelInfoVO;
import com.norm.timemall.app.team.service.TeamOasisHtmlAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class TeamHtmlChannelController {
    @Autowired
    public TeamOasisHtmlAppService teamOasisHtmlAppService;
    @ResponseBody
    @GetMapping(value = "/api/v1/team/app/html/{oasis_channel_id}/info")
    public FetchHtmlChannelInfoVO fetchHtmlChannelInfo(@PathVariable("oasis_channel_id") String oasisChannelId){
        String htmlCode=teamOasisHtmlAppService.findHtmlCode(oasisChannelId);
        FetchHtmlChannelInfoVO vo = new FetchHtmlChannelInfoVO();
        vo.setHtmlCode(htmlCode);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @ResponseBody
    @PutMapping(value = "/api/v1/team/app/html/edit")
    public SuccessVO editHtmlContent(@RequestBody @Validated EditHtmlContentDTO dto){

        teamOasisHtmlAppService.modifyHtmlContent(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }

}
