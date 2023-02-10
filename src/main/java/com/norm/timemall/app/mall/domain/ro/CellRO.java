package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CellRO {
    private String brandId;
    private String brand;
    private String avator;
    private String preview;
    private String title;
    private BigDecimal price;
    private String id;
    private String sbu;
// 激活蓝标： 0 未激活 1 激活
    private String enableBlue;
}
