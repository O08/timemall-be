package com.norm.timemall.app.mall.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import com.norm.timemall.app.base.enums.ProductStatusEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class MallRetrieveBrandProductListPageDTO extends PageDTO {
    // search keyword
    private String q;
    @NotBlank(message = "brandId is required")
    private String brandId;

    @NotBlank(message = "status required")
    @EnumCheck(enumClass = ProductStatusEnum.class,message = "field: status, incorrect parameter value ,option: draft-1; online-2; offline-3")
    private String status;

}
