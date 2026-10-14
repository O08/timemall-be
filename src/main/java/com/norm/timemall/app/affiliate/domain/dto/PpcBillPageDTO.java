package com.norm.timemall.app.affiliate.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PpcBillPageDTO extends PageDTO {
    private String q;
    private String sort;
    private String tag;

    @Positive(message = "ipVolumeLeft value should be positive.")
    private Integer ipVolumeLeft;
    @Positive(message = "ipVolumeRight value should be positive.")
    private Integer ipVolumeRight;

    @Positive(message = "amountLeft value should be positive.")
    private Integer amountLeft;
    @Positive(message = "amountRight value should be positive.")
    private Integer amountRight;

}
