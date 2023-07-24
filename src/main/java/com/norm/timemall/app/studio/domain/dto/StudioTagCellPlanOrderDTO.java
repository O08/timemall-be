package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StudioTagCellPlanOrderDTO {
    @NotBlank(message = "tag required")
    private String tag;
}
