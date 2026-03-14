package com.norm.timemall.app.studio.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class StudioGetSpaceSubscriptionPlanPageDTO extends PageDTO {
    @NotBlank(message = "sellerBrandId required")
    private String sellerBrandId;
}
