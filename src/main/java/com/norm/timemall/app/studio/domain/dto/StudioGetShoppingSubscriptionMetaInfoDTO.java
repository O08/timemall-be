package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioGetShoppingSubscriptionMetaInfoDTO {
    @NotBlank(message = "sellerHandle required")
    private String sellerHandle;
    @NotBlank(message = "productCode required")
    private String productCode;

}
