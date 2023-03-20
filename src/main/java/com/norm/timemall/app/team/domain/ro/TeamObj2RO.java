package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TeamObj2RO {
    private String id;
    private String title;
    private String sbu;
    private String quantity;
    private BigDecimal salePrice;
    private BigDecimal objectVal;
    private String debitId;
    private String creditId;
}
