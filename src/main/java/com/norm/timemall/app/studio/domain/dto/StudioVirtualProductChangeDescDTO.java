package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
@Data
public class StudioVirtualProductChangeDescDTO {

    @NotBlank(message = "productDesc required")
    @Length(message = "productDesc length must in range {min}-{max}",min = 1,max = 11000)
    private String productDesc;

    @NotBlank(message = "productId required")
    private String productId;

}
