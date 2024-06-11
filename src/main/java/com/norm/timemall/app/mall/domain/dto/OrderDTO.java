package com.norm.timemall.app.mall.domain.dto;

import com.norm.timemall.app.base.enums.SbuEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * 下单dto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class OrderDTO extends AffiliateDTO{
    // The Quantity of sbu
    @NotNull(message = "quantity is required and must be number")
    @Positive(message = "quantity value should be positive.")
    private Integer quantity;
    // Standard Billing Unit SBU
    @NotBlank(message = "sbu is required,option: year quarter month day hour minute second")
    @EnumCheck(enumClass = SbuEnum.class,message = "field: sbu, incorrect paramter value ,option: year quarter month day hour minute second")
    private String sbu;
}
