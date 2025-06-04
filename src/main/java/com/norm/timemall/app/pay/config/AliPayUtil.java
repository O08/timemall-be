package com.norm.timemall.app.pay.config;

import com.alipay.api.AlipayConfig;
import com.norm.timemall.app.base.config.env.EnvBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.rmi.ServerException;


public class AliPayUtil {
    public static   AlipayConfig getAlipayConfig(AliPayResource aliPayResource, EnvBean envBean) {
        AlipayConfig alipayConfig = new AlipayConfig();
        alipayConfig.setPrivateKey(aliPayResource.getMerchantPrivateKey());
        alipayConfig.setServerUrl(aliPayResource.getGatewayUrl());
        alipayConfig.setAppId(aliPayResource.getAppId());
        alipayConfig.setCharset(aliPayResource.getCharset());
        alipayConfig.setSignType(aliPayResource.getSignType());
        alipayConfig.setFormat("json");

        String basePath = "";
        try {
            basePath = queryPath(aliPayResource);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }

        if(envBean.getSoftwareDevelopmentLifeCycle().equals("dev")){
            alipayConfig.setAppCertPath(basePath+aliPayResource.getDevAppCertPath());
            alipayConfig.setAlipayPublicCertPath(basePath+aliPayResource.getDevAlipayPublicCertPath());
            alipayConfig.setRootCertPath(basePath+aliPayResource.getDevRootCertPath());
        }
        if(envBean.getSoftwareDevelopmentLifeCycle().equals("prod")
        ){
            alipayConfig.setAppCertPath(basePath+aliPayResource.getProdAppCertPath());
            alipayConfig.setAlipayPublicCertPath(basePath+aliPayResource.getProdAlipayPublicCertPath());
            alipayConfig.setRootCertPath(basePath+aliPayResource.getProdRootCertPath());
        }

        return alipayConfig;
    }

    public static String getAliPayPublicCertPath(AliPayResource aliPayResource,EnvBean envBean){
        String basePath = "";
        try {
            basePath = queryPath(aliPayResource);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (ServerException e) {
            throw new RuntimeException(e);
        }

        if(envBean.getSoftwareDevelopmentLifeCycle().equals("dev")){
            return basePath + aliPayResource.getDevAlipayPublicCertPath();
        }

        return basePath + aliPayResource.getProdAlipayPublicCertPath();
    }

    /**
     * 证书模式   获取文件路径
     * 不这样把证书复制出来服务器上会获取不到证书路径
     *
     * @return
     * @throws FileNotFoundException
     * @throws ServerException
     */
    public static String queryPath(AliPayResource aliPayResource) throws FileNotFoundException, ServerException {
        String basePath = ResourceUtils.getURL("classpath:").getPath();
        basePath = initCrt(basePath, aliPayResource);
        return basePath;
    }

    /**
     * 证书模式 初始化证书文件
     *
     * @param basePath
     * @return
     * @throws ServerException
     */
    private static String initCrt(String basePath,AliPayResource aliPayResource) throws ServerException {
        if (basePath.contains("jar!")) {
            if (basePath.startsWith("file:")) {
                basePath = basePath.replace("file:", "");
            }
            doInitDevCrt(basePath,aliPayResource);
            doInitProdCrt(basePath,aliPayResource);
        }
        return basePath;
    }
    private static void doInitDevCrt(String basePath,AliPayResource aliPayResource)  throws ServerException {
        checkAndcopyCart(basePath, aliPayResource.getDevAppCertPath());
        checkAndcopyCart(basePath, aliPayResource.getDevAlipayPublicCertPath());
        checkAndcopyCart(basePath, aliPayResource.getDevRootCertPath());
    }
    private static void doInitProdCrt(String basePath,AliPayResource aliPayResource)  throws ServerException {
        checkAndcopyCart(basePath, aliPayResource.getProdAppCertPath());
        checkAndcopyCart(basePath, aliPayResource.getProdAlipayPublicCertPath());
        checkAndcopyCart(basePath, aliPayResource.getProdRootCertPath());
    }


    /**
     * 证书模式下需要
     *
     * @param basePath
     * @param path
     * @throws ServerException
     * @description 查找在该文件路径下是否已经存在这个文件，如果不存在，则拷贝文件
     */
    private static void checkAndcopyCart(String basePath, String path) throws ServerException {
        InputStream cartInputStream = null;
        OutputStream cartOutputStream = null;
        String cartPath = basePath + path;
        File cartFile = new File(cartPath);
        File parentFile = cartFile.getParentFile();
        parentFile.mkdirs();
        try {
            if (!cartFile.exists()) {
                cartInputStream = ClassUtils.getDefaultClassLoader().getResourceAsStream(path);
                cartOutputStream = new FileOutputStream(cartFile);
                byte[] buf = new byte[1024];
                int bytesRead;
                while ((bytesRead = cartInputStream.read(buf)) != -1) {
                    cartOutputStream.write(buf, 0, bytesRead);
                }
                cartInputStream.close();
                cartOutputStream.close();
            }
        } catch (IOException e) {
            throw new ServerException(e.getMessage());
        }
    }



}
