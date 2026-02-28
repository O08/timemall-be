package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.team.domain.pojo.TeamAppViberPostEmbed;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppViberCreatePostDTO {
    @NotBlank(message = "Channel is required")
    private String channel;

    private TeamAppViberPostEmbed embed;

    @Length(message = "textMsg length must in range {min}-{max}",min = 0,max = 600)
    private String textMsg;
}
