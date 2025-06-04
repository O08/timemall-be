package com.norm.timemall.app.pay.config;

import com.alipay.api.AlipayConfig;
import com.norm.timemall.app.base.config.env.EnvBean;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class AliPayUtil {
    public static   AlipayConfig getAlipayConfig(AliPayResource aliPayResource, EnvBean envBean) {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setPrivateKey(aliPayResource.getMerchantPrivateKey());
        alipayConfig.setServerUrl(aliPayResource.getGatewayUrl());
        alipayConfig.setAppId(aliPayResource.getAppId());
        alipayConfig.setCharset(aliPayResource.getCharset());
        alipayConfig.setSignType(aliPayResource.getSignType());
        alipayConfig.setFormat("json");

        if(envBean.getSoftwareDevelopmentLifeCycle().equals("local")){
            alipayConfig.setAppCertPath(aliPayResource.getLocalAppCertPath());
            alipayConfig.setAlipayPublicCertPath(aliPayResource.getLocalAlipayPublicCertPath());
            alipayConfig.setRootCertPath(aliPayResource.getLocalRootCertPath());
        }
        if(envBean.getSoftwareDevelopmentLifeCycle().equals("deploy")
        ){
            alipayConfig.setAppCertPath(aliPayResource.getDeployAppCertPath());
            alipayConfig.setAlipayPublicCertPath(aliPayResource.getDeployAlipayPublicCertPath());
            alipayConfig.setRootCertPath(aliPayResource.getDeployRootCertPath());
        }

        return alipayConfig;
    }

    public static String getAliPayPublicCertPath(AliPayResource aliPayResource,EnvBean envBean){


        if(envBean.getSoftwareDevelopmentLifeCycle().equals("local")){
            return aliPayResource.getLocalAlipayPublicCertPath();
        }

        return aliPayResource.getDeployAlipayPublicCertPath();
    }



}
