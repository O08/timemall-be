package com.norm.timemall.app.team.domain.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class PutChannelGeneralDTO {
    @NotBlank(message = "channelDesc required")
    @Length(message = "channelDesc length must in range {min}-{max}",min = 1,max = 80)
    private String channelDesc;
    @NotBlank(message = "channelName required")
    @Length(message = "channelName length must in range {min}-{max}",min = 1,max = 30)
    private String channelName;
    @NotBlank(message = "oasisChannelId required")
    private String oasisChannelId;
}
