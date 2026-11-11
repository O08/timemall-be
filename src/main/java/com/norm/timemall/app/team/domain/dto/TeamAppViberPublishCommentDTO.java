package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppViberPublishCommentDTO {

    @NotBlank(message = "postId required")
    private String postId;

    @NotBlank(message = "textMsg required")
    @Length(message = "textMsg length must in range {min}-{max}", min = 1, max = 500)
    private String textMsg;

}
