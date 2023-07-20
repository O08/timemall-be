package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioCellPlanOrderPageRO {
    private String orderId;
    private String cellTitle;
    private String planTypeDesc;
    private String price;
    private String tag;
    private String createAt;
}
