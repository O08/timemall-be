package com.norm.timemall.app.pod.domain.ro;

import lombok.Data;

@Data
public class FetchBillDetailRO {
    private String amount;
    private String creditPoint;
    private String earlyBirdDiscount;
    private String item;
    private String repurchaseDiscount;
}
