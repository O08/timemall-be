package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.studio.domain.pojo.StudioBrandLinksWrapper;
import lombok.Data;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Data
public class StudioBrandLinksDTO {
    @Valid
    @NotNull(message = "link is required")
    private StudioBrandLinksWrapper link;
}
