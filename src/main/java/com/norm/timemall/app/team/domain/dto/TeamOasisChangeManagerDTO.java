package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamOasisChangeManagerDTO {

    @NotBlank(message = "oasisId required")
    private String oasisId;

    @NotBlank(message = "newManagerBrandId required")
    private String newManagerBrandId;

    @NotBlank(message = "newManagerName required")
    private String newManagerName;


}
