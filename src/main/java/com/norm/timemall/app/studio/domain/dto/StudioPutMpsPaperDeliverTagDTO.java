package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class StudioPutMpsPaperDeliverTagDTO {
    @NotBlank(message = "tag required")
    private String tag;
    @NotBlank(message = "paperId required")
    private String deliverId;
}
