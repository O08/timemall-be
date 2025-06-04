package com.norm.timemall.app.pay.controller;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConstants;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.norm.timemall.app.base.config.env.EnvBean;
import com.norm.timemall.app.base.mo.ProprietaryTradingOrder;
import com.norm.timemall.app.base.mo.ProprietaryTradingPayment;
import com.norm.timemall.app.pay.config.AliPayResource;
import com.norm.timemall.app.pay.config.AliPayUtil;
import com.norm.timemall.app.pay.helper.PayHelper;
import com.norm.timemall.app.studio.service.StudioProprietaryTradingOrderService;
import com.norm.timemall.app.studio.service.StudioProprietaryTradingPaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/payment")
public class AlipayController {

    @Autowired
    private AliPayResource aliPayResource;

    @Autowired
    private EnvBean envBean;

    @Autowired
    private StudioProprietaryTradingOrderService studioProprietaryTradingOrderService;

    @Autowired
    private StudioProprietaryTradingPaymentService studioProprietaryTradingPaymentService;

    @Autowired
    private PayHelper payHelper;

    /**
     * 前往支付宝进行支付
     */
    @GetMapping(value="/goAlipay.html")
    public String goAlipay(String merchantUserId, String merchantOrderId) throws Exception{
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AliPayUtil.getAlipayConfig(aliPayResource,envBean));


        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(aliPayResource.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayResource.getNotifyUrl());
        ProprietaryTradingOrder proprietaryTradingOrder = studioProprietaryTradingOrderService.getById(merchantOrderId);


        // 商户订单号, 商户网站订单系统中唯一订单号, 必填
        String out_trade_no = merchantOrderId;
        // 付款金额, 必填 单位元
        String total_amount = proprietaryTradingOrder.getTotal().toString();  // 测试用 1分钱
        // 订单名称, 必填
        String subject = payHelper.getPaySubject(proprietaryTradingOrder.getTradingId())
                +"-付款[" + merchantUserId + "]";
        // 商品描述, 可空, 目前先用订单名称
        String body = subject;

        // 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
        String timeout_express = "1h";
        alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\","
                + "\"total_amount\":\""+ total_amount +"\","
                + "\"subject\":\""+ subject +"\","
                + "\"body\":\""+ body +"\","
                + "\"timeout_express\":\""+ timeout_express +"\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

        //请求
        String alipayForm = "";
        try {
            alipayForm = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        log.info("支付宝支付 - 前往支付页面, alipayForm: \n{}", alipayForm);
        return alipayForm;
    }


    /**
     * 支付成功后的支付宝异步通知
     */
    @PostMapping(value="/alipay")
    public String alipay(HttpServletRequest request, HttpServletResponse response) throws Exception {

        log.info("支付成功后的支付宝异步通知");

        //获取支付宝POST过来反馈信息
        Map<String,String> params = new HashMap<String,String>();
        Map<String,String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = iter.next();
            String[] values = requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用
//       valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            params.put(name, valueStr);
        }

        String paramsJson = JSON.toJSONString(params);
        log.info("支付宝回调，{}", paramsJson);


        boolean signVerified = AlipaySignature.rsaCertCheckV1(params,
                AliPayUtil.getAliPayPublicCertPath(envBean),
                AlipayConstants.CHARSET_UTF8,
                AlipayConstants.SIGN_TYPE_RSA2);


        if(signVerified) {//验证成功
            // 商户订单号
            String out_trade_no = new String(request.getParameter("out_trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 支付宝交易号
            String trade_no = new String(request.getParameter("trade_no").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 交易状态
            String trade_status = new String(request.getParameter("trade_status").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            // 付款金额
            String total_amount = new String(request.getParameter("total_amount").getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);

            if (trade_status.equals("TRADE_SUCCESS")){
                ProprietaryTradingPayment proprietaryTradingPayment = payHelper.generatePaymentWhenSuccessForAliPay(out_trade_no, trade_no, trade_status, total_amount, paramsJson);
                studioProprietaryTradingPaymentService.insertTradingPayment(proprietaryTradingPayment);
                studioProprietaryTradingOrderService.updateTradingOrderStatusAsPaid(out_trade_no);
                payHelper.busiHandler(params);
//                String merchantReturnUrl = paymentOrderService.updateOrderPaid(out_trade_no, CurrencyUtils.getYuan2Fen(total_amount));
//                notifyFoodieShop(out_trade_no,merchantReturnUrl);
            }

            log.info("************* 支付成功(支付宝异步通知) - 时间: {} *************", DateUtil.date());
            log.info("* 订单号: {}", out_trade_no);
            log.info("* 支付宝交易号: {}", trade_no);
            log.info("* 实付金额: {}", total_amount);
            log.info("* 交易状态: {}", trade_status);
            log.info("*****************************************************************************");

            return "success";
        }else {
            //验证失败
            log.info("验签失败, 时间: {}", DateUtil.date());
            return "fail";
        }
    }



}

