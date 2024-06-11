package com.norm.timemall.app.affiliate.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class FetchApiMarketingPageDTO extends PageDTO {
    private String q;
    private Integer viewsLeft;
    private Integer viewsRight;
    private Integer revshareLeft;
    private Integer revshareRight;
    private Integer saleVolumeLeft;
    private Integer saleVolumeRight;
    private Integer salesLeft;
    private Integer salesRight;
    private Integer supplierAccountAgeLeft;
    private Integer supplierAccountAgeRight;
    private String heartbeat;
    private String blue;
    private Integer planPriceLeft;
    private Integer planPriceRight;
    private String offline;
}
