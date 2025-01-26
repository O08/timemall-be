package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppFbPostCommentDTO {

    @NotBlank(message = "feedId required")
    private String feedId;

    @NotBlank(message = "content required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 172)
    private String content;

}
