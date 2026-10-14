package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

import jakarta.validation.Valid;

@Data
public class StudioBrandLinksWrapper {
    @Valid
    private StudioBrandLinkEntry[] records;
}
