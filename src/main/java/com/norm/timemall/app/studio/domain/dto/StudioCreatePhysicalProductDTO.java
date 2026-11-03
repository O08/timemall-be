package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class StudioCreatePhysicalProductDTO {
    private MultipartFile thumbnail;

    @Pattern(regexp="^\\w+$", message="商品编码字符不符合要求")
    @Length(message = "productCode range in {min}-{max}",min = 1,max = 38)
    @NotBlank(message = "productCode required")
    private String productCode;

    @Length(message = "productName range in {min}-{max}",min = 1,max = 32)
    @NotBlank(message = "productName required")
    private String productName;

    @Length(message = "productDesc range in {min}-{max}",min = 1,max = 1000)
    @NotBlank(message = "productDesc required")
    private String productDesc;

    @NotBlank(message = "warmReminder required")
    @Length(message = "warmReminder length must in range {min}-{max}",min = 1,max = 500)
    private String warmReminder;

    @NotNull(message = "price is required")
    @DecimalMin(value = "0.01", message = "price must be at least 0.01")
    @DecimalMax(value = "999999.99", message = "price cannot exceed 999,999.99")
    private BigDecimal price;

    @NotNull(message = "inventory required")
    @PositiveOrZero(message = "inventory required and must be positive or zero")
    @Max(value = 999999, message = "inventory cannot exceed 999,999")
    private Integer inventory;
}
