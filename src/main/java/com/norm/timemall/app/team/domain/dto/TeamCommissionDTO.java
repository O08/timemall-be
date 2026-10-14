package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class TeamCommissionDTO extends PageDTO  {
    // search keyword
    private String q;
    @NotBlank(message = "oasisId required")
    private String oasisId;
    private String tag;
    private String sort;
    private String filter;
    private String worker;

}
