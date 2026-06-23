package com.norm.timemall.app.pay.domain.pojo;

import lombok.Data;

import java.math.BigDecimal;
// ref: https://opendocs.alipay.com/apis/00fka9
@Data
public class BizContentForUniTransfer {
    private String out_biz_no;
    private String payee_type;
    private String payee_account;
    private BigDecimal amount;
    private String payer_show_name;
    private String payee_real_name;
    /**
     * 业务备注
     */
    private String remark;


}
