package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MallCellRO {
    private String preview;
    private String title;
    private BigDecimal price;
    private String id;
    private String brandId;
    private String sbu;
}
