package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppCardGuideLayoutEnum;
import com.norm.timemall.app.base.enums.AppCardGuidePostStrategyEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppCardChannelSettingDTO {
    @NotBlank(message = "layout required")
    @EnumCheck(enumClass = AppCardGuideLayoutEnum.class,message = "field: layout , enum: art ,video, film, book,design,music")
    private String layout;

    @NotBlank(message = "postStrategy required")
    @EnumCheck(enumClass = AppCardGuidePostStrategyEnum.class,message = "field: postStrategy , enum: 1 -- all ,2 -- only admin")
    private String postStrategy;

    @NotBlank(message = "channelId required")
    private String channelId;
}
