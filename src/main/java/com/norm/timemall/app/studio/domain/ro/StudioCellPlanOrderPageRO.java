package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudioCellPlanOrderPageRO {
    private String orderId;
    private String cellTitle;
    private String planTypeDesc;
    private String price;
    private String tag;
    private String createAt;
    private String planType;
    private BigDecimal netIncome;
    private BigDecimal commission;
    private BigDecimal revenue;
    private BigDecimal promotionDeduction;
}
