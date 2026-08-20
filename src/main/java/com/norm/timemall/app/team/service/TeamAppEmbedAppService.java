package com.norm.timemall.app.team.service;

import com.norm.timemall.app.team.domain.dto.TeamAppEmbedWebSettingDTO;
import com.norm.timemall.app.team.domain.ro.TeamAppFetchEmbedWebChannelInfoRO;
import org.springframework.stereotype.Service;

@Service
public interface TeamAppEmbedAppService {
    TeamAppFetchEmbedWebChannelInfoRO fetchEmbedWebChannelInfo(String channelId);

    void modifyEmbedWebSetting(TeamAppEmbedWebSettingDTO dto);
}
