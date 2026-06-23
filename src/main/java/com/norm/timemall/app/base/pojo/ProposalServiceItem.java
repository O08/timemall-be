package com.norm.timemall.app.base.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProposalServiceItem {
    private String serviceName;
    private BigDecimal quantity;
    private BigDecimal price;

}
