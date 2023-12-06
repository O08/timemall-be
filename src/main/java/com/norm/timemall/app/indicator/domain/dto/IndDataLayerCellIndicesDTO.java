package com.norm.timemall.app.indicator.domain.dto;

import com.norm.timemall.app.indicator.domain.pojo.IndDataLayerCellIndicesBO;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class IndDataLayerCellIndicesDTO {

    private String event;
    @NotNull(message = "cell is required")
    private IndDataLayerCellIndicesBO cell;

}
