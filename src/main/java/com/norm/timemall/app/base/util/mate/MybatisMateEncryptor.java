package com.norm.timemall.app.base.util.mate;

import mybatis.mate.annotation.Algorithm;

import mybatis.mate.config.EncryptorProperties;
import mybatis.mate.encrypt.IEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MybatisMateEncryptor  {

    @Autowired
    private EncryptorProperties encryptorProperties;

    @Autowired
    private IEncryptor encryptor;

    public String defaultEncrypt(String plaintext){
        try {
            return  encrypt(Algorithm.AES, encryptorProperties.getPassword(), encryptorProperties.getPublicKey(), plaintext, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     *
     * @param algorithm  算法
     * @param password   密码（对称加密算法密钥）
     * @param plaintext  明文
     * @param publicKey  非对称加密算法（公钥）
     * @param metaObject {@link org.apache.ibatis.reflection.MetaObject}
     * @return
     */
    public String encrypt(Algorithm algorithm, String password, String publicKey, String plaintext, Object metaObject) throws Exception {
        // 可以判断 plaintext 是否为空加密，为空不加密直接返回 plaintext
        return encryptor.encrypt(algorithm, password, publicKey, plaintext, metaObject);
    }

    /**
     * 解密
     *
     * @param algorithm  算法
     * @param password   密码（对称加密算法密钥）
     * @param encrypt    密文
     * @param privateKey 非对称加密算法（私钥）
     * @param metaObject {@link org.apache.ibatis.reflection.MetaObject}
     * @return
     */
    public String decrypt(Algorithm algorithm, String password, String privateKey, String encrypt, Object metaObject) throws Exception {
        // 解密密文 encrypt
        return encryptor.decrypt(algorithm, password, privateKey, encrypt, metaObject);
    }
}