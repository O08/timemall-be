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
    private String planType;
    private String revenue;
    private String promotionDeduction;
    private String sellerName;
    private String sellerAvatar;
    private String sellerUserId;
}
