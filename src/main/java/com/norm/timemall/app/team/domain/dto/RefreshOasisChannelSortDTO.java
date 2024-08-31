package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class RefreshOasisChannelSortDTO {
    @NotBlank(message = "oasisId required")
    private String oasisId;
    private String sortJson;
}
