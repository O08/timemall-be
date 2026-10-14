package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class FetchDelAccountRequirementRO {
    private String itemCode;
    private String item;
    private String targetVal;
    private String currentVal;
    private String landUrl;
    private String createAt;
}
