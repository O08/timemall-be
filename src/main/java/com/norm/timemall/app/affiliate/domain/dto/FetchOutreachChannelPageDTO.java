package com.norm.timemall.app.affiliate.domain.dto;

import com.norm.timemall.app.base.entity.PageDTO;
import lombok.Data;

@Data
public class FetchOutreachChannelPageDTO extends PageDTO {
    private String q;
    private Integer viewsLeft;
    private Integer viewsRight;
    private Integer saleVolumeLeft;
    private Integer saleVolumeRight;
    private Integer salesLeft;
    private Integer salesRight;
}
