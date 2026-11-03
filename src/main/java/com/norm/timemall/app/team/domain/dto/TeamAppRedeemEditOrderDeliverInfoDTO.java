package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

@Data
public class TeamAppRedeemEditOrderDeliverInfoDTO {
    private MultipartFile deliveryMaterial;

    @NotBlank(message = "orderId required")
    private String orderId;

    @NotBlank(message = "deliverNote required")
    @Length(message = "deliverNote length must in range {min}-{max}",min = 1,max = 200)
    private String deliveryNote;
}
