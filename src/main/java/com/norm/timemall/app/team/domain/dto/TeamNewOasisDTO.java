package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class TeamNewOasisDTO {
    @NotBlank(message = "title required")
    private String title;
    @NotBlank(message = "subTitle required")
    private String subTitle;
}
