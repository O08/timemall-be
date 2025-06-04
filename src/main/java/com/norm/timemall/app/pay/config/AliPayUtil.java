package com.norm.timemall.app.pay.config;

import com.alipay.api.AlipayConfig;
import com.norm.timemall.app.base.config.env.EnvBean;
import lombok.extern.slf4j.Slf4j;

import java.io.File;


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

        File file5 = new File("/tmp/data/logs/timemall-be.2025-05-29.0.log");
        File file6 = new File("/tmp/data/deploy/alipay-crt/alipayRootCert.crt");
        File file7 = new File("tmp/data/deploy/alipay-crt/alipayRootCert.crt");

        log.info("file5 exist:" +file5.exists());
        log.info("file6 exist:" +file6.exists());
        log.info("file7 exist:" +file7.exists());


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
