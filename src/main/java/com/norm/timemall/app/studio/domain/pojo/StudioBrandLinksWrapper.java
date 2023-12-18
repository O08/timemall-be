package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

import javax.validation.Valid;

@Data
public class StudioBrandLinksWrapper {
    @Valid
    private StudioBrandLinkEntry[] records;
}
