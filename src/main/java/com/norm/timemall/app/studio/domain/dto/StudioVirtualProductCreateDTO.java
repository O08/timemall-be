package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.enums.SwitchCheckEnum;
import com.norm.timemall.app.base.validator.EnumCheck;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class StudioVirtualProductCreateDTO {

    private MultipartFile thumbnail;

    @NotBlank(message = "productName required")
    @Length(message = "productName length must in range {min}-{max}",min = 1,max = 38)
    private String productName;

    @NotNull(message = "productPrice required")
    @Positive(message = "productPrice required and must be positive")
    private BigDecimal productPrice;

    @NotNull(message = "inventory required")
    @Positive(message = "inventory required and must be positive")
    private Integer inventory;

    @NotBlank(message = "provideInvoice required")
    @EnumCheck(enumClass = SwitchCheckEnum.class,message = "field: provideInvoice, incorrect parameter value ,option: on-1; off-0;")
    private String provideInvoice;

    private String tags;
}
