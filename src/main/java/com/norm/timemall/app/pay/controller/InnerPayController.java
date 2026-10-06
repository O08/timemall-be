package com.norm.timemall.app.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayFundTransUniTransferModel;
import com.alipay.api.domain.Participant;
import com.alipay.api.request.AlipayFundTransUniTransferRequest;
import com.alipay.api.response.AlipayFundTransUniTransferResponse;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.entity.SuccessVO;
import com.norm.timemall.app.base.enums.CodeEnum;
import com.norm.timemall.app.base.exception.ErrorCodeException;
import com.norm.timemall.app.pay.config.AliPayResource;
import com.norm.timemall.app.pay.config.AliPayUtil;
import com.norm.timemall.app.pay.domain.dto.WithDrawDTO;
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
    private EnvBean envBean;
    @Autowired
    private TeamWithdrawService teamWithdrawService;

    /**
     * 用户提现接口
     */
    @PostMapping(value="/api/v1/team/withdraw_to_alipay")
    public SuccessVO withdraw(@RequestBody WithDrawDTO dto) throws AlipayApiException {
        //获得初始化的AlipayClient

        AlipayClient alipayClient = new DefaultAlipayClient(AliPayUtil.getAlipayConfig(aliPayResource,envBean));


        WithdrawToALiPayBO bo =  teamWithdrawService.toAliPay(dto);

        //设置收款方信息
        Participant payeeInfo = new Participant();
        payeeInfo.setIdentity(bo.getPayeeAccount());
        payeeInfo.setName(bo.getPayeeRealName());
        payeeInfo.setIdentityType("ALIPAY_LOGON_ID");

        AlipayFundTransUniTransferModel model = new AlipayFundTransUniTransferModel();
        // 设置商家侧唯一订单号
        model.setOutBizNo(bo.getOrderNo());

        // 设置订单总金额
        model.setTransAmount(""+bo.getAmount());

        // 设置描述特定的业务场景
        model.setBizScene("DIRECT_TRANSFER");

        // 设置业务产品码
        model.setProductCode("TRANS_ACCOUNT_NO_PWD");

        // 设置转账业务的标题
        model.setOrderTitle("群巅-提现");

        model.setPayeeInfo(payeeInfo);

        // 设置业务备注
        model.setRemark("群巅-提现");

        // 设置转账业务请求的扩展参数
        model.setBusinessParams("{\"payer_show_name_use_alias\":\"true\"}");


        AlipayFundTransUniTransferRequest request = new AlipayFundTransUniTransferRequest();
        request.setBizModel(model);
        AlipayFundTransUniTransferResponse response = alipayClient.certificateExecute(request);

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
