package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.studio.domain.pojo.StudioCellIntroContent;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class StudioCellIntroContentDTO {
    @NotNull(message = "content node required")
    private StudioCellIntroContent content;
}
