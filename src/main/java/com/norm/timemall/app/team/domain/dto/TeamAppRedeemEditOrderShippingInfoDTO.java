package com.norm.timemall.app.team.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class TeamAppRedeemEditOrderShippingInfoDTO {
    @Length(message = "consignee length must in range {min}-{max}",min = 0,max = 80)
    private String consignee;

    @NotBlank(message = "orderId required")
    private String orderId;

    @Length(message = "shippingAddress length must in range {min}-{max}",min = 0,max = 80)
    private String shippingAddress;

    @Length(message = "shippingEmail length must in range {min}-{max}",min = 0,max = 80)
    private String shippingEmail;
}
