package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppFetchEmbedWebChannelInfoDTO {
    @NotBlank(message = "channelId required")
    private String channelId;
}
