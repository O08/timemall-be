package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StudioSubsProductChangeDTO {
    @Length(message = "productName range in {min}-{max}",min = 1,max = 32)
    @NotBlank(message = "productName required")
    private String productName;

    @Pattern(regexp="^\\w+$", message="商品编码字符不符合要求")
    @Length(message = "productCode range in {min}-{max}",min = 1,max = 36)
    @NotBlank(message = "productCode required")
    private String productCode;

    @Length(message = "productDesc range in {min}-{max}",min = 1,max = 172)
    @NotBlank(message = "productDesc required")
    private String productDesc;

    @NotBlank(message = "productId required")
    private String productId;
}
