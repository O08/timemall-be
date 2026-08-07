package com.norm.timemall.app.base.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TransferBO {
    private String payeeType;
    // 收款人
    private String payeeAccount;
    private BigDecimal amount;
    private String payerType;
    // 付款人
    private String payerAccount;
    private String transType;
    private String outNo;
}
