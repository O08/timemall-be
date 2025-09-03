package com.norm.timemall.app.studio.domain.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class StudioBlueSign {
    private String tradingName;
    private String id;
    private String tradingDesc;
    private BigDecimal price;
    private Date blueBegainAt;
    private Date blueEndAt;

}
