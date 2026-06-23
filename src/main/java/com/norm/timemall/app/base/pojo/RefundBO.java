package com.norm.timemall.app.base.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class RefundBO {

    private String tradeNo;
    private String outNo;
}
