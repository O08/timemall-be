package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppEmbedWebSettingDTO {

    @NotBlank(message = "channelDesc required")
    @Length(message = "channelDesc length must in range {min}-{max}",min = 1,max = 80)
    private String channelDesc;
    @NotBlank(message = "channelName required")
    @Length(message = "channelName length must in range {min}-{max}",min = 1,max = 30)
    private String channelName;

    @NotBlank(message = "channelId required")
    private String channelId;

    @Length(message = "webUri length must in range {min}-{max}",min = 1,max = 500)
    @NotBlank(message = "webUri required")
    private String webUri;
}
