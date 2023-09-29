package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudioTransRO {
    private String service;
    private String customer;
    private BigDecimal fee;
    private String added;
    private String id;
    private String customerUserId;
}
