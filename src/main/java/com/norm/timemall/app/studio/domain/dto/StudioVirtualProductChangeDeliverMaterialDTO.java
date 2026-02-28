package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class StudioVirtualProductChangeDeliverMaterialDTO {

    private MultipartFile deliverAttachment;

    @NotBlank(message = "productId required")
    private String productId;

    @NotBlank(message = "deliverNote required")
    @Length(message = "deliverNote length must in range {min}-{max}",min = 1,max = 900)
    private String deliverNote;

}
