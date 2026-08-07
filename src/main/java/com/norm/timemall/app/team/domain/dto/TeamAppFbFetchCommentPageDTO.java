package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppFbFetchCommentPageDTO extends PageDTO {

    @NotBlank(message = "feedId required")
    private String feedId;

}
