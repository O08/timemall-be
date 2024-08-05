package com.norm.timemall.app.studio.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class FetchCellCouponBenefitDTO {
    @NotBlank(message = "supplierBrandId required")
    private String supplierBrandId;
    @NotBlank(message = "cellId required")
    private String cellId;
}
