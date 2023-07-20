package com.norm.timemall.app.base.pojo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@Accessors(chain = true)
public class TransferBO {
    private String payeeType;
    private String payeeAccount;
    private BigDecimal amount;
    private String payerType;
    private String payerAccount;
    private String transType;
    private String outNo;
}
