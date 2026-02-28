package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class StudioBrandLinkEntry {
    @NotBlank(message = "linkTitle is required")
    private String linkTitle;
    @NotBlank(message = "uri is required")
    private String uri;
}
