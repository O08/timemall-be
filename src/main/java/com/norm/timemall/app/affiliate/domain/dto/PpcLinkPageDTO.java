package com.norm.timemall.app.affiliate.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PpcLinkPageDTO extends PageDTO {

    private String q;

    @Positive(message = "viewsLeft value should be positive.")
    private Integer viewsLeft;
    @Positive(message = "viewsRight value should be positive.")
    private Integer viewsRight;

    @Positive(message = "ipVolumeLeft value should be positive.")
    private Integer ipVolumeLeft;
    @Positive(message = "ipVolumeRight value should be positive.")
    private Integer ipVolumeRight;

    @Positive(message = "earningLeft value should be positive.")
    private Integer earningLeft;
    @Positive(message = "earningRight value should be positive.")
    private Integer earningRight;

    private String sort;



}
