package com.norm.timemall.app.base.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class WechatConfig {
    @Value("${wechat.webapp-id}")
    private String webappId;
    @Value("${wechat.webapp-secret}")
    private String webappSecret;
    @Value("${wechat.token-uri}")
    private String tokenUri;
    @Value("${wechat.user-info-uri}")
    private String userInfoUri;
}
