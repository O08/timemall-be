package com.norm.timemall.app.pod.domain.pojo;

import lombok.Data;

@Data
public class PodPhysicalOrderRefundHandlerParam {
    private String orderId;
    private String refundReason;
}
