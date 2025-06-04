package com.norm.timemall.app.pay.config;

import com.alipay.api.AlipayConfig;
import com.norm.timemall.app.base.config.env.EnvBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import java.io.IOException;

public class AliPayUtil {
    public static AlipayConfig getAlipayConfig(AliPayResource aliPayResource, EnvBean envBean) {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setPrivateKey(aliPayResource.getMerchantPrivateKey());
        alipayConfig.setServerUrl(aliPayResource.getGatewayUrl());
        alipayConfig.setAppId(aliPayResource.getAppId());
        alipayConfig.setCharset(aliPayResource.getCharset());
        alipayConfig.setSignType(aliPayResource.getSignType());
        alipayConfig.setFormat("json");

        if(envBean.getSoftwareDevelopmentLifeCycle().equals("dev")){
            alipayConfig.setAppCertPath("src/main/resources/dev/alipay-crt/appPublicCert.crt");
            alipayConfig.setAlipayPublicCertPath("src/main/resources/dev/alipay-crt/alipayPublicCert.crt");
            alipayConfig.setRootCertPath("src/main/resources/dev/alipay-crt/alipayRootCert.crt");
        }
        if(envBean.getSoftwareDevelopmentLifeCycle().equals("prd")){
            alipayConfig.setAppCertPath("<-- 请填写您的应用公钥证书文件路径，例如：/foo/appCertPublicKey_2019051064521003.crt -->");
            alipayConfig.setAlipayPublicCertPath("<-- 请填写您的支付宝公钥证书文件路径，例如：/foo/alipayCertPublicKey_RSA2.crt -->");
            alipayConfig.setRootCertPath("<-- 请填写您的支付宝根证书文件路径，例如：/foo/alipayRootCert.crt -->");
        }

        return alipayConfig;
    }
    public static String getAliPayPublicCertPath(EnvBean envBean){
        if(envBean.getSoftwareDevelopmentLifeCycle().equals("dev")){
            return "src/main/resources/dev/alipay-crt/alipayPublicCert.crt";
        }

        return "src/main/resources/alipay-crt/alipayPublicCert.crt";
    }
}
