package com.norm.timemall.app.team.service;

import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.norm.timemall.app.pay.domain.dto.WithDrawDTO;
import com.norm.timemall.app.team.domain.pojo.WithdrawToALiPayBO;
import org.springframework.stereotype.Service;

@Service
public interface TeamWithdrawService {
    WithdrawToALiPayBO toAliPay(WithDrawDTO dto);

    void toAliPaySuccess(String orderNo, AlipayFundTransUniTransferResponse response);

    void toAliPayFail(String orderNo, AlipayFundTransUniTransferResponse response);
}
