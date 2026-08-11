package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PodBillsRO {
    // 收费项目 ref: bill.stage
    private String stage;

    // 服务供应商 ref: brand.brand_name
    private String brand;

    private String brandAvatar;

    private String brandUserId;

    // 服务名称 ref: cell.title
    private String service;

    // 结款金额 ref: bill.amount
    private BigDecimal amount;

    // ref: bill.id
    private String billId;

    // 支付凭证 ref: bill.voucher
    private String voucher;
    // ref: bill.create_at
    private String added;
    // ref: brand.id
    private String brandId;
    private String promotionDeduction;
    private String categories;

}
