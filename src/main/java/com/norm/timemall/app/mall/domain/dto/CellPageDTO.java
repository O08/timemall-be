package com.norm.timemall.app.mall.domain.dto;

import com.norm.timemall.app.base.enums.SbuEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import lombok.Data;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

/**
 * 服务列表分页查询数据结构
 */
@Data
@Accessors(chain = true)
public class CellPageDTO {
    // search keyword
    private String q;
    @NotNull(message = "current is required and must be number")
    @Positive(message = "current value should be positive.")
    private Long current;
    @NotNull(message = "size is required and must be number")
    @Positive(message = "size value should be positive.")
    private Long size;
    // 预算
    @Positive(message = "budget max value should be positive.")
    private BigDecimal budgetMin;
    @Positive(message = "budget max value should be positive.")
    private BigDecimal budgetMax;

    // 排序方式
    private String sort;

    // Standard Billing Unit SBU , option: year quarter month day hour minute second
    @NotBlank(message = "sbu is required,option: year quarter month day hour minute second")
    @EnumCheck(enumClass = SbuEnum.class,message = "field: sbu, incorrect paramter value ,option: year quarter month week day hour minute second")
    private String sbu;

    private String location;
    private boolean online;


}
