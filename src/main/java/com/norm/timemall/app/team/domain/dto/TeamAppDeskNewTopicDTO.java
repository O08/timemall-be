package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppDeskNewTopicDTO {

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 0,max = 72)
    private String title;

    @NotBlank(message = "chn required")
    private String chn;

    @Length(message = "preface length must in range {min}-{max}",min = 0,max = 100)
    private String preface;

}
