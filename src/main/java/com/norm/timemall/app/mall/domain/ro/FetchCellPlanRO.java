package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class FetchCellPlanRO {
    private String planId;
    private String title;
    private String feature;
    private String price;
    private String planType;
}
