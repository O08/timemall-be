package com.norm.timemall.app.team.domain.ro;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class OfficeEmployeeCompensationRO {
    private String title;
    private BigDecimal amount;
    private Integer direction;
}
