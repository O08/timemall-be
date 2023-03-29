package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.team.domain.pojo.TeamOasisRisk;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamOasisRiskDTO {
    //TeamOasisRisk

    private String risk;
    @NotBlank(message = "oasisId required")
    private String oasisId;

}
