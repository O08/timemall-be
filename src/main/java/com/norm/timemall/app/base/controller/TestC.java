package com.norm.timemall.app.base.controller;

import cn.hutool.crypto.SecureUtil;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class TestC {
    /**
     * 类型
     */
    public static final String ENCRYPT_TYPE = "RSA";

    /**
     * 获取公钥的key
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * 获取私钥的key
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    public static void main(String[] args) {
        Map<String, String> stringStringMap = TestC.generateKeyPair();

        String RSAPublicKey = stringStringMap.get(PUBLIC_KEY);

        String RSAPrivateKey = stringStringMap.get(PRIVATE_KEY);

        System.out.println(RSAPublicKey);
        System.out.println("**************************");
        System.out.println(RSAPrivateKey);
        System.out.println("##########################");



    }
    public static Map<String, String> generateKeyPair() {
        try {
            KeyPair pair = SecureUtil.generateKeyPair(ENCRYPT_TYPE);
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();
            // 获取 公钥和私钥 的 编码格式（通过该 编码格式 可以反过来 生成公钥和私钥对象）
            byte[] pubEncBytes = publicKey.getEncoded();
            byte[] priEncBytes = privateKey.getEncoded();

            // 把 公钥和私钥 的 编码格式 转换为 Base64文本 方便保存
            String pubEncBase64 = Base64.getEncoder().encodeToString(pubEncBytes);
            String priEncBase64 = Base64.getEncoder().encodeToString(priEncBytes);

            Map<String, String> map = new HashMap<String, String>(2);
            map.put(PUBLIC_KEY, pubEncBase64);
            map.put(PRIVATE_KEY, priEncBase64);

            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
