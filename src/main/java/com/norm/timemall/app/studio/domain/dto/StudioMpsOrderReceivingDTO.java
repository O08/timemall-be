package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StudioMpsOrderReceivingDTO {
    @NotBlank(message = "paperId is required")
    private String paperId;
}
