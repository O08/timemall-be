package com.norm.timemall.app.pay.service;

import com.norm.timemall.app.base.pojo.TransferBO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface DefaultPayService {
    String transfer(String param);
    void refund(String param);
    TransferBO generateTransferBO(String transType,String payeeType,String payeeAccount,String payerType, String payerAccount,BigDecimal amount, String outNo);
}
