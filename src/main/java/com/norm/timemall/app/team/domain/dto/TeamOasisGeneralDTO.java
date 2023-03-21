package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamOasisGeneralDTO {
    @NotBlank(message = "title required")
    private String title;
    @NotBlank(message = "subTitle required")
    private String subTitle;
    @NotBlank(message = "oasisId required")
    private String oasisId;
}
