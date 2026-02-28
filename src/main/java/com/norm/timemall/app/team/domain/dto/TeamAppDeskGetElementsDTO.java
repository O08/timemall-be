package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamAppDeskGetElementsDTO {

    private String q;
    @NotBlank(message = "chn required")
    private String chn;

}
