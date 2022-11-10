package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

@Data
public class StudioBillRO {
    private String stage;
    private String customer;
    private String service;
    private String amount;
    private String billId;
    private String voucher;
}
