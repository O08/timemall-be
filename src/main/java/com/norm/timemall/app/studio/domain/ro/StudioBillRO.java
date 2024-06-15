package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudioBillRO {
    private String stage;
    private String customer;
    private String service;
    private String amount;
    private String billId;
    private String voucher;
    private String added;
    private BigDecimal netIncome;
    private BigDecimal commission;
}
