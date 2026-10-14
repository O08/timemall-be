package com.norm.timemall.app.studio.domain.ro;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StudioGetElectricityProductInfoRO {
    /**
     * 源能
     */
    private String electricity;
    /**
     * 物品id
     */
    private String id;
    /**
     * 物品价格
     */
    private BigDecimal price;
    /**
     * 物品简介
     */
    private String tradingDesc;
    /**
     * 物品名称
     */
    private String tradingName;
}
