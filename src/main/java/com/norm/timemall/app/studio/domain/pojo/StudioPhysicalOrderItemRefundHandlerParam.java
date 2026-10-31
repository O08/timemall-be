package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

@Data
public class StudioPhysicalOrderItemRefundHandlerParam {
    private String orderItemId;
    private String refundReason;
    private String term;
}
