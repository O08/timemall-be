package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.studio.domain.pojo.StudioBrandLinksWrapper;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class StudioBrandLinksDTO {
    @Valid
    @NotNull(message = "link is required")
    private StudioBrandLinksWrapper link;
}
