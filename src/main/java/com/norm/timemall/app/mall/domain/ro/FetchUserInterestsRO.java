package com.norm.timemall.app.mall.domain.ro;

import lombok.Data;

@Data
public class FetchUserInterestsRO {
    private String itemCode;
    private String item;
    private String val;
    private String landUrl;
    private String createAt;
}
