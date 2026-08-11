package com.norm.timemall.app.team.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamFetchOasisMemberPageDTO extends PageDTO {
    private String q;
    @NotBlank(message = "oasisId required")
    private String oasisId;
}
