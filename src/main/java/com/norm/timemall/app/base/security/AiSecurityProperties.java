package com.norm.timemall.app.base.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "blv.security.ai")
@PropertySource("classpath:ai-security.properties")
@Data
public class AiSecurityProperties {
    private String apiKey;
    // 至少32位秘钥
    private String jwtSecret;
    private List<String> allowPaths;
    private List<String> authPaths;
}
