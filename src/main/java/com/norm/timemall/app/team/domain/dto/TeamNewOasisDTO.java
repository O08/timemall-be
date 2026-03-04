package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamNewOasisDTO {

    @NotBlank(message = "title required")
    @Length(message = "title length must in range {min}-{max}",min = 1,max = 80)
    private String title;

    @NotBlank(message = "subTitle required")
    @Length(message = "subTitle length must in range {min}-{max}",min = 1,max = 200)
    private String subTitle;

}
