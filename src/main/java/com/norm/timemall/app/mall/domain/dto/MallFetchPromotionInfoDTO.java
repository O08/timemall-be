package com.norm.timemall.app.mall.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class MallFetchPromotionInfoDTO {
    @NotBlank(message = "brandId  required")
    private String brandId;
}
