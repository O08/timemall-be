package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class TeamMarkObjDTO {
    @NotBlank(message = "swapNo required")
    private String swapNo;
    @NotBlank(message = "mark required")
    private String mark;
}
