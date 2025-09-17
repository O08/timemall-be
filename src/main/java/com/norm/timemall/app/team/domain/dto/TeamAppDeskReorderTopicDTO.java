package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppDeskTopicReorderEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppDeskReorderTopicDTO {
    @NotBlank(message = "topicId required")
    private String topicId;

    @NotBlank(message = "direction required")
    @EnumCheck(enumClass = AppDeskTopicReorderEnum.class,message = "field: direction, incorrect parameter value ,option: up; down;")
    private String direction;

}
