package com.norm.timemall.app.mall.domain.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

// 分页查询商家上线的服务
@Data
@Accessors(chain = true)
public class BrandCellsPageDTO {
    @NotBlank(message = "brandId is required")
    private String brandId;
    @NotNull(message = "current is required and must be number")
    @Positive(message = "current value should be positive.")
    private Long current;
    @NotNull(message = "size is required and must be number")
    @Positive(message = "size value should be positive.")
    private Long size;
}
