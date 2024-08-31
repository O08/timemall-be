package com.norm.timemall.app.team.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EditHtmlContentDTO {

    private String html;

    @NotBlank(message = "oasisChannelId required")
    private String oasisChannelId;

}
