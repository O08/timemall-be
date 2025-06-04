package com.norm.timemall.app.pay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix= "alipay")
@PropertySource("classpath:alipay.properties")
public class AliPayResource {
    private String appId;
    private String merchantPrivateKey;
    private String alipayPublicKey;

    private String notifyUrl;
    private String returnUrl;

    private String signType;
    private String charset;
    private String gatewayUrl;

    private String devAppCertPath;
    private String devAlipayPublicCertPath;
    private String devRootCertPath;

    private String prodAppCertPath;
    private String prodAlipayPublicCertPath;
    private String prodRootCertPath;

}
