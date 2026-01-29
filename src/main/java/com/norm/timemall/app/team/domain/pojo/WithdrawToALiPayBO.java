package com.norm.timemall.app.team.domain.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithdrawToALiPayBO {
    private String orderNo;
    private String payeeAccount;
    private BigDecimal amount;
    private String payeeRealName;
    private String title;
}
