package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppFbFeedPinEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppFbPinFeedDTO {

    @NotBlank(message = "tag required")
    @EnumCheck(enumClass = AppFbFeedPinEnum.class,message = "field: tag, 0 - pin off； 1 - pin on")
    private String tag;

    @NotBlank(message = "feedId required")
    private String feedId;
}
