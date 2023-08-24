package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamExamineOasisTaskDTO {

    @NotBlank(message = "commissionId required")
    private String commissionId;

    @NotBlank(message = "tag required")
    private String tag;

}
