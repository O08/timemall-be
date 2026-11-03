package com.norm.timemall.app.marketing.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MktClaimRedeemDTO {
    @NotBlank(message = "giftCode required")
    private String giftCode;
}
