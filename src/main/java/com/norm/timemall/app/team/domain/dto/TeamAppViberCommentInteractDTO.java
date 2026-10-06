package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.enums.AppViberCommentInteractEventEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppViberCommentInteractDTO {

    @NotBlank(message = "cid required")
    private String cid;

    @NotBlank(message = "event required")
    @EnumCheck(enumClass = AppViberCommentInteractEventEnum.class, message = "event must be '1-like' or '2-dislike'")
    private String event;

}
