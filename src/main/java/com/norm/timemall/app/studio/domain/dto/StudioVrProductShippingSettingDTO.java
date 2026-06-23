package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.VirtualProductShippingMethodEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StudioVrProductShippingSettingDTO {
    @NotBlank(message = "productId required")
    private String productId;

    @Length(message = "pack range in {min}-{max}",min = 0,max = 300)
    private String pack;

    @NotBlank(message = "shippingMethod required")
    @EnumCheck(enumClass = VirtualProductShippingMethodEnum.class,message = "field: shippingMethod, incorrect parameter value ,option: standard; random; manual")
    private String shippingMethod;
}
