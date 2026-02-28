package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamFetchRoleChannelDTO {
    private String q;

    @NotBlank(message = "roleId required")
    private String roleId;

    @NotBlank(message = "oasisId required")
    private String oasisId;
}
