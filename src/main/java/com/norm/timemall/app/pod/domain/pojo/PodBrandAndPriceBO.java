package com.norm.timemall.app.pod.domain.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PodBrandAndPriceBO {
    private String brandId;
    private BigDecimal amount;
}
