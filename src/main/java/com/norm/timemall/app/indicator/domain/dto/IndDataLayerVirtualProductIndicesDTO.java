package com.norm.timemall.app.indicator.domain.dto;

import com.norm.timemall.app.indicator.domain.pojo.IndDataLayerVirtualProductIndicesBO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IndDataLayerVirtualProductIndicesDTO {
    
    @NotBlank(message = "event is required")
    private String event;

    @NotNull(message = "virtual is required")
    private IndDataLayerVirtualProductIndicesBO virtual;

}
