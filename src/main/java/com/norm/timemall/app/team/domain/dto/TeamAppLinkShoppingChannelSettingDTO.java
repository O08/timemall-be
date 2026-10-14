package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppLinkShoppingChannelSettingDTO {

    @NotBlank(message = "enableMemberPost required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: enableMemberPost, incorrect parameter value ,option: on-1; off-0;")
    private String enableMemberPost;

    @NotBlank(message = "channelDesc required")
    @Length(message = "channelDesc length must in range {min}-{max}",min = 1,max = 80)
    private String channelDesc;

    @NotBlank(message = "channelName required")
    @Length(message = "channelName length must in range {min}-{max}",min = 1,max = 30)
    private String channelName;

    @NotBlank(message = "channelId required")
    private String channelId;

}
