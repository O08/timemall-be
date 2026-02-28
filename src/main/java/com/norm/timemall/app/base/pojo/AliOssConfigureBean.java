package com.norm.timemall.app.base.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class AliOssConfigureBean {
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @Value("${aliyun.oss.access-key-id}")
    private String accessKeyId;
    @Value("${aliyun.oss.access-key-secret}")
    private String accessKeySecret;
    // 有公共读权限
    @Value("${aliyun.oss.public-bucket}")
    private String publicBucket;
    // 个人 、无公共读、写权限
    @Value("${aliyun.oss.limited-bucket}")
    private String limitedBucket;
}
