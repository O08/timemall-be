package com.norm.timemall.app.base.config;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.norm.timemall.app.base.pojo.AliOssConfigureBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AliOssConfig {
    @Bean
    public OSS ossClient(AliOssConfigureBean aliOssConfigure) {
        // 使用单例，由 Spring 管理生命周期
        return new OSSClientBuilder().build(
                aliOssConfigure.getEndpoint(),
                aliOssConfigure.getAccessKeyId(),
                aliOssConfigure.getAccessKeySecret()
        );
    }
}
