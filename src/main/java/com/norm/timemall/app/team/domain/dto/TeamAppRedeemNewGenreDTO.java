package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppRedeemNewGenreDTO {

    @NotBlank(message = "channel required")
    private String channel;

    @NotBlank(message = "genreName required")
    @Length(message = "genreName length must in range {min}-{max}",min = 1,max = 20)
    private String genreName;
}
