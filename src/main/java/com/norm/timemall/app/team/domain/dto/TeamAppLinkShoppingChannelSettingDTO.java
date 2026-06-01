package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppLinkShoppingChannelSettingDTO {

    @NotBlank(message = "enableMemberPost required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: enableMemberPost, incorrect parameter value ,option: on-1; off-0;")
    private String enableMemberPost;

    @NotBlank(message = "channelId required")
    private String channelId;

}
