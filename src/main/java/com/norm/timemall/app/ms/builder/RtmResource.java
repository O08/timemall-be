package com.norm.timemall.app.ms.builder;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="agora")
@PropertySource("classpath:webrtc.properties")
public class RtmResource {
    private String appId;
    private String appCertificate;
    private int expirationInSeconds;
}
