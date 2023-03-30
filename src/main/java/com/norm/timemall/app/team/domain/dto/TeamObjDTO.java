package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamObjDTO {
    @NotBlank(message = "swapNo required")
    private String swapNo;
    @NotBlank(message = "od required")
    private String od;
}
