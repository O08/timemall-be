package com.norm.timemall.app.base.entity;

import lombok.Data;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
public class PageDTO {
    @NotNull(message = "current is required and must be number")
    @Positive(message = "current value should be positive.")
    private Long current;
    @NotNull(message = "size is required and must be number")
    @Positive(message = "size value should be positive.")
    private Long size;
}
