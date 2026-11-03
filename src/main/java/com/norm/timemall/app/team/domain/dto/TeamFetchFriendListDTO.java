package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TeamFetchFriendListDTO {

    private String q;

    @NotBlank(message = "oasisId required")
    private String oasisId;
}
