package com.norm.timemall.app.mall.domain.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class InsertOrderParameter {
    // uuid ,order detial id
    private String id;
    private String userId;

    private String cellId;

    private int quantity;

    private String sbu;

}
