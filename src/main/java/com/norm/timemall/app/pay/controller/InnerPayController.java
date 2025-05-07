package com.norm.timemall.app.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.pay.config.AliPayResource;
import com.norm.timemall.app.pay.domain.dto.WithDrawDTO;
import com.norm.timemall.app.pay.domain.pojo.BizContentForUniTransfer;
import com.norm.timemall.app.team.domain.pojo.WithdrawToALiPayBO;
import com.norm.timemall.app.team.service.TeamWithdrawService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class InnerPayController {
    @Autowired
    private AliPayResource aliPayResource;
    @Autowired
    private TeamWithdrawService teamWithdrawService;

    /**
     * 用户提现接口
     */
    @PostMapping(value="/api/v1/team/withdraw_to_alipay")
    public SuccessVO withdraw(@RequestBody WithDrawDTO dto) throws AlipayApiException {
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(aliPayResource.getGatewayUrl(),
                aliPayResource.getAppId(),
                aliPayResource.getMerchantPrivateKey(),
                "json",
                aliPayResource.getCharset(),
                aliPayResource.getAlipayPublicKey(),
                aliPayResource.getSignType());


        WithdrawToALiPayBO bo =  teamWithdrawService.toAliPay(dto);

        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
//        request.setBizContent("{" +
//                "  \"out_biz_no\":\""+"3142321423432\"," +
//                "  \"payee_type\":\"ALIPAY_LOGONID\"," +
//                "  \"payee_account\":\"abc@sina.com\"," +
//                "  \"amount\":\"12.23\"," +
//                "  \"payer_show_name\":\"群巅-提现\"," +
//                "  \"payee_real_name\":\"张三\"," +
//                "  \"remark\":\"提现\"" +
//                "}");
//转账参数
        BizContentForUniTransfer bizContent = new BizContentForUniTransfer();
        bizContent.setOut_biz_no(bo.getOrderNo());
        bizContent.setPayee_type("ALIPAY_LOGONID");
        bizContent.setPayee_account(bo.getPayeeAccount());
        bizContent.setAmount(bo.getAmount());
        bizContent.setPayer_show_name("群巅-提现");
        bizContent.setPayee_real_name(bo.getPayeeRealName());
        bizContent.setRemark("提现");

        request.setBizContent(JSON.toJSONString(bizContent));

        AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);
        log.info("orderNO:"+bo.getOrderNo()+"提现返回："+JSON.toJSONString(response));
        if(response.isSuccess()){
            teamWithdrawService.toAliPaySuccess(bo.getOrderNo(),response);
        } else {
            teamWithdrawService.toAliPayFail(bo.getOrderNo(),response);
            throw new ErrorCodeException(CodeEnum.FAILED);
        }
        return new SuccessVO(CodeEnum.SUCCESS);
    }
}
