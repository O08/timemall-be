package com.norm.timemall.app.pod.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PodVirtualOrderApplyRefundDTO {

    @NotBlank(message = "orderId required")
    private String orderId;

    @NotBlank(message = "refundReason required")
    @Length(message = "refundReason length must in range {min}-{max}",min = 1,max = 200)
    private String refundReason;

}
