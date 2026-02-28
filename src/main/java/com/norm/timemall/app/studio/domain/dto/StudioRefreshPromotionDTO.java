package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.BrandPromotionTagEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class StudioRefreshPromotionDTO {

    @NotNull(message = "creditPoint is required")
    @Range(min = 1L,max = 50000L,message = "creditPoint range in {min} - {max}")
    private Integer creditPoint;

    @NotBlank(message = "creditPointTag required")
    @EnumCheck(enumClass = BrandPromotionTagEnum.class,message = "field: creditPointTag, incorrect paramter value ,option: 1 2")
    private String creditPointTag;

    @NotNull(message = "earlyBirdDiscount is required")
    @Range(min = 1L,max = 99L,message = "earlyBirdDiscount range in {min} - {max}")
    private Integer earlyBirdDiscount;

    @NotBlank(message = "earlyBirdDiscountTag required")
    @EnumCheck(enumClass = BrandPromotionTagEnum.class,message = "field: earlyBirdDiscountTag, incorrect paramter value ,option: 1 2")
    private String earlyBirdDiscountTag;

    @NotNull(message = "repurchaseDiscount is required")
    @Range(min = 1L,max = 99L,message = "repurchaseDiscount range in {min} - {max}")
    private Integer repurchaseDiscount;

    @NotBlank(message = "repurchaseDiscountTag required")
    @EnumCheck(enumClass = BrandPromotionTagEnum.class,message = "field: repurchaseDiscountTag, incorrect paramter value ,option: 1 2")
    private String repurchaseDiscountTag;

}
