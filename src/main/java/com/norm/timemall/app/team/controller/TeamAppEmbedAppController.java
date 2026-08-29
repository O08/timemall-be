package com.norm.timemall.app.team.controller;

import cn.hutool.core.lang.Validator;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.QuickMessageException;
import com.norm.timemall.app.team.domain.dto.PutChannelGeneralDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppEmbedWebSettingDTO;
import com.norm.timemall.app.team.domain.dto.TeamAppFetchEmbedWebChannelInfoDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppFetchEmbedWebChannelInfoRO;
import com.norm.timemall.app.team.domain.vo.TeamAppFetchEmbedWebChannelInfoVO;
import com.norm.timemall.app.team.service.TeamAppEmbedAppService;
import com.norm.timemall.app.team.service.TeamDataPolicyService;
import com.norm.timemall.app.team.service.TeamOasisChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamAppEmbedAppController {
    @Autowired
    private TeamAppEmbedAppService teamAppEmbedAppService;

    @Autowired
    private TeamDataPolicyService teamDataPolicyService;

    @Autowired
    private TeamOasisChannelService teamOasisChannelService;

    @GetMapping("/api/v1/app/embed_web/info")
    public TeamAppFetchEmbedWebChannelInfoVO getEmbedWebInfo(TeamAppFetchEmbedWebChannelInfoDTO dto){
        TeamAppFetchEmbedWebChannelInfoRO channel = teamAppEmbedAppService.fetchEmbedWebChannelInfo(dto.getChannelId());
        TeamAppFetchEmbedWebChannelInfoVO vo = new TeamAppFetchEmbedWebChannelInfoVO();
        vo.setChannel(channel);
        vo.setResponseCode(CodeEnum.SUCCESS);
        return vo;
    }

    @PutMapping("/api/v1/app/embed_web/setting")
    public SuccessVO updateEmbedWebSetting(@RequestBody @Validated TeamAppEmbedWebSettingDTO dto){
        // validate web uri
        if(!Validator.isUrl(dto.getWebUri())){
            throw new QuickMessageException("web uri invalid");
        }

        PutChannelGeneralDTO channelGeneralDTO = new PutChannelGeneralDTO();
        channelGeneralDTO.setOasisChannelId(dto.getChannelId());
        channelGeneralDTO.setChannelName(dto.getChannelName());
        channelGeneralDTO.setChannelDesc(dto.getChannelDesc());

        teamOasisChannelService.modifyChannelGeneralInfo(channelGeneralDTO);

        teamAppEmbedAppService.modifyEmbedWebSetting(dto);

        return new SuccessVO(CodeEnum.SUCCESS);
    }

}
