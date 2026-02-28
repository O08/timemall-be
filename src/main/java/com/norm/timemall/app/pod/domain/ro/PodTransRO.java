package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PodTransRO {
    // The cel title for cell, ref: cell.title
    private String service;
    // The brand_name for brand, ref: brand.brand_name
    private String supplier;
    private String supplierAvatar;
    // The total fee for order,ref: order_details.total
    private BigDecimal fee;
    // Create date for order, ref: order_details.create_at
    private String added;
    // The id for brand, ref: brand.id
    private String brandId;
    // The customer_id for brand, ref: brand.customer_id
    private String supplierUserId;
    private String sbu;
    private String quantity;
}
