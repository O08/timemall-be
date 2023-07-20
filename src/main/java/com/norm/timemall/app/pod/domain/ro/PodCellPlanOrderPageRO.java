package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;

@Data
public class PodCellPlanOrderPageRO {
    private String orderId;
    private String cellTitle;
    private String planTypeDesc;
    private String price;
    private String tag;
    private String createAt;
}
