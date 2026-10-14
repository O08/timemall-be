package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppViberPostInteractEventEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppViberPostInteractDTO {

    @NotBlank(message = "postId required")
    private String postId;

    @NotBlank(message = "event required")
    @EnumCheck(enumClass = AppViberPostInteractEventEnum.class, message = "event must be '1-like', '2-cancel_like', or '3-share'")
    private String event;

}