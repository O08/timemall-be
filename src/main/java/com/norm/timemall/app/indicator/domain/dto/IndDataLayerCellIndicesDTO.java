package com.norm.timemall.app.indicator.domain.dto;

import com.norm.timemall.app.indicator.domain.pojo.IndDataLayerCellIndicesBO;
import com.norm.timemall.app.mall.domain.dto.AffiliateDTO;
import lombok.Data;

import jakarta.validation.constraints.NotNull;

@Data
public class IndDataLayerCellIndicesDTO {

    private String event;
    @NotNull(message = "cell is required")
    private IndDataLayerCellIndicesBO cell;
    private AffiliateDTO affiliate;

}
