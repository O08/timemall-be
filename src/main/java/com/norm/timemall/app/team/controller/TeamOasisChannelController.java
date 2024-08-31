package com.norm.timemall.app.team.controller;

import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.team.domain.dto.PutChannelGeneralDTO;
import com.norm.timemall.app.team.domain.ro.FetchOasisChannelListRO;
import com.norm.timemall.app.team.domain.vo.FetchOasisChannelListVO;
import com.norm.timemall.app.team.service.TeamOasisChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class TeamOasisChannelController {
    @Autowired
    private TeamOasisChannelService teamOasisChannelService;
    @ResponseBody
    @GetMapping(value = "/api/v1/team/oasis/{oasis_id}/oasis_channel/list")
    public FetchOasisChannelListVO fetchOasisChannelList(@PathVariable("oasis_id") String oasisId){

        ArrayList<FetchOasisChannelListRO> ro = teamOasisChannelService.findOasisChannelList(oasisId);
        FetchOasisChannelListVO vo = new FetchOasisChannelListVO();
        vo.setChannel(ro);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;

    }
    @ResponseBody
    @DeleteMapping(value = "/api/v1/team/oasis/channel/{oasis_channel_id}/remove")
    public SuccessVO removeOasisChannel(@PathVariable("oasis_channel_id")String oasisChannelId){

        teamOasisChannelService.removeOasisChannel(oasisChannelId);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
    @ResponseBody
    @PutMapping("/api/v1/team/oasis/channel/general")
    public SuccessVO settingChannelGeneralInfo(@RequestBody @Validated PutChannelGeneralDTO dto){

        teamOasisChannelService.modifyChannelGeneralInfo(dto);
        return new SuccessVO(CodeEnum.SUCCESS);

    }
}
