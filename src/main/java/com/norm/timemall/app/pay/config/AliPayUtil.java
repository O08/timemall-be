package com.norm.timemall.app.pay.config;

import com.alipay.api.AlipayConfig;
import com.norm.timemall.app.base.config.env.EnvBean;
import org.springframework.core.io.ClassPathResource;
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
            alipayConfig.setAppCertPath(getServerPath("dev/alipay-crt/appPublicCert.crt"));
            alipayConfig.setAlipayPublicCertPath(getServerPath("dev/alipay-crt/alipayPublicCert.crt"));
            alipayConfig.setRootCertPath(getServerPath("dev/alipay-crt/alipayRootCert.crt"));
        }
        if(envBean.getSoftwareDevelopmentLifeCycle().equals("prod")){
            alipayConfig.setAppCertPath(getServerPath("alipay-crt/appPublicCert.crt"));
            alipayConfig.setAlipayPublicCertPath(getServerPath("alipay-crt/alipayPublicCert.crt"));
            alipayConfig.setRootCertPath(getServerPath("alipay-crt/alipayRootCert.crt"));
        }

        return alipayConfig;
    }
    public static String getAliPayPublicCertPath(EnvBean envBean){
        if(envBean.getSoftwareDevelopmentLifeCycle().equals("dev")){
            return getServerPath("dev/alipay-crt/alipayPublicCert.crt");
        }

        return getServerPath("alipay-crt/alipayPublicCert.crt");
    }
    private  static   String getServerPath(String file){
        try {
            return   new ClassPathResource(file).getURL().getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
