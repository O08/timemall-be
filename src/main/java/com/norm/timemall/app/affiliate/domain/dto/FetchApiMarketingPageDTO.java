package com.norm.timemall.app.affiliate.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class FetchApiMarketingPageDTO extends PageDTO {
    private String q;
    @Positive(message = "viewsLeft value should be positive.")
    private Integer viewsLeft;
    @Positive(message = "viewsRight value should be positive.")
    private Integer viewsRight;
    @Positive(message = "revshareLeft value should be positive.")
    private Integer revshareLeft;
    @Positive(message = "revshareRight value should be positive.")
    private Integer revshareRight;
    @Positive(message = "saleVolumeLeft value should be positive.")
    private Integer saleVolumeLeft;
    @Positive(message = "saleVolumeRight value should be positive.")
    private Integer saleVolumeRight;
    @Positive(message = "salesLeft value should be positive.")
    private Integer salesLeft;
    @Positive(message = "salesRight value should be positive.")
    private Integer salesRight;
    @Positive(message = "supplierAccountAgeLeft value should be positive.")
    private Integer supplierAccountAgeLeft;
    @Positive(message = "supplierAccountAgeRight value should be positive.")
    private Integer supplierAccountAgeRight;
    private String heartbeat;
    private String blue;
    @Positive(message = "planPriceLeft value should be positive.")
    private Integer planPriceLeft;
    @Positive(message = "planPriceRight value should be positive.")
    private Integer planPriceRight;
    private String offline;
    private String sort;
}
