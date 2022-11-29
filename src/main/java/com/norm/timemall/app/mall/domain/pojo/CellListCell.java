package com.norm.timemall.app.mall.domain.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CellListCell {
    private String preview;
    private String title;
    private BigDecimal price;
    private String id;
    private String sbu;
}
