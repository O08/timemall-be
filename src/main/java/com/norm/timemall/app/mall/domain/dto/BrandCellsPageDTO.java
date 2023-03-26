package com.norm.timemall.app.mall.domain.dto;

import com.norm.timemall.app.base.enums.SbuEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
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

    private String q;
    @NotNull(message = "current is required and must be number")
    @Positive(message = "current value should be positive.")
    private Long current;
    @NotNull(message = "size is required and must be number")
    @Positive(message = "size value should be positive.")
    private Long size;
    // Standard Billing Unit SBU , option: year quarter month day hour minute second
    @NotBlank(message = "sbu is required,option: year quarter month day hour minute second")
    @EnumCheck(enumClass = SbuEnum.class,message = "field: sbu, incorrect paramter value ,option: year quarter month week day hour minute second")
    private String sbu;
}
