package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppFbGuideLayoutEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppFbChannelSettingDTO {

    @NotBlank(message = "layout required")
    @EnumCheck(enumClass = AppFbGuideLayoutEnum.class,message = "field: layout, 0 - gallery； 1 - list")
    private String layout;

    @NotBlank(message = "channelId required")
    private String channelId;

}
