package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppFbFeedCommentFeatureTagEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppFbToggleCommentDTO {

    @NotBlank(message = "tag required")
    @EnumCheck(enumClass = AppFbFeedCommentFeatureTagEnum.class,message = "field: tag, 0 - off； 1 - on")
    private String tag;

    @NotBlank(message = "feedId required")
    private String feedId;

}
