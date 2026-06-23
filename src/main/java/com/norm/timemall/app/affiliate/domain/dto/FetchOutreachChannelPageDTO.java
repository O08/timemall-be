package com.norm.timemall.app.affiliate.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

import jakarta.validation.constraints.Positive;

@Data
public class FetchOutreachChannelPageDTO extends PageDTO {
    private String q;
    @Positive(message = "viewsLeft value should be positive.")
    private Integer viewsLeft;
    @Positive(message = "viewsRight value should be positive.")
    private Integer viewsRight;
    @Positive(message = "saleVolumeLeft value should be positive.")
    private Integer saleVolumeLeft;
    @Positive(message = "saleVolumeRight value should be positive.")
    private Integer saleVolumeRight;
    @Positive(message = "salesLeft value should be positive.")
    private Integer salesLeft;
    @Positive(message = "salesRight value should be positive.")
    private Integer salesRight;
    private String sort;
    private String brandId;
}
