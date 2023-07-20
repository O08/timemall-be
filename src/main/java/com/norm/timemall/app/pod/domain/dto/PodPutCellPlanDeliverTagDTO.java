package com.norm.timemall.app.pod.domain.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PodPutCellPlanDeliverTagDTO {
    @NotBlank(message = "tag required")
    private String tag;
    @NotBlank(message = "deliverId required")
    private String deliverId;
    @NotBlank(message = "orderId required")
    private String orderId;
}
