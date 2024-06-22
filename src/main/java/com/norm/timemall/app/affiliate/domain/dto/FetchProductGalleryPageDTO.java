package com.norm.timemall.app.affiliate.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
public class FetchProductGalleryPageDTO extends PageDTO {
    private String q;
    @Positive(message = "supplierAccountAgeLeft value should be positive.")
    private Integer supplierAccountAgeLeft;
    private String heartbeat;
    private String blue;
    @Positive(message = "planPriceLeft value should be positive.")
    private Integer planPriceLeft;
    @PositiveOrZero(message = "provideInvoice value should be positive or zero.")
    private Integer provideInvoice;
    @Positive(message = "revshareLeft value should be positive.")
    private Integer revshareLeft;
    @Positive(message = "viewsLeft value should be positive.")
    private Integer viewsLeft;
    @Positive(message = "viewsRight value should be positive.")
    private Integer viewsRight;
    @Positive(message = "planPriceRight value should be positive.")
    private Integer planPriceRight;
    @Positive(message = "revshareRight value should be positive.")
    private Integer revshareRight;
    @Positive(message = "supplierAccountAgeRight value should be positive.")
    private Integer supplierAccountAgeRight;
    @Positive(message = "saleVolumeLeft value should be positive.")
    private Integer saleVolumeLeft;
    @Positive(message = "saleVolumeRight value should be positive.")
    private Integer saleVolumeRight;
    @Positive(message = "influencersLeft value should be positive.")
    private Integer influencersLeft;
    @Positive(message = "influencersRight value should be positive.")
    private Integer influencersRight;
    private String brandId;
    private String sort;

}
