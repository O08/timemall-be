package com.norm.timemall.app.affiliate.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class FetchProductGalleryPageDTO extends PageDTO {
    private String q;
    private Integer supplierAccountAgeLeft;
    private String heartbeat;
    private String blue;
    private Integer planPriceLeft;
    private String provideInvoice;
    private Integer revshareLeft;
    private Integer viewsLeft;
    private Integer viewsRight;
    private Integer planPriceRight;
    private Integer revshareRight;
    private Integer supplierAccountAgeRight;
    private Integer saleVolumeLeft;
    private Integer saleVolumeRight;
    private Integer influencersLeft;
    private Integer influencersRight;
    private String brandId;

}
