package com.norm.timemall.app.studio.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioSubsGetShoppingOfferDTO {
    @NotBlank(message = "promoCode required")
    private String promoCode;

    @NotBlank(message = "sellerBrandId required")
    private String sellerBrandId;
}
