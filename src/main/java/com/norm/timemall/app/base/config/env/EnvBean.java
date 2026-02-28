package com.norm.timemall.app.base.config.env;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class EnvBean {
    @Value("${env.website}")
    private String website;
    @Value("${env.short-link-site}")
    private String shortSite;
    @Value("${env.life-cycle}")
    private String softwareDevelopmentLifeCycle;
    /**
     * 密码重置有效区
     */
    long tokenOut = 3600000L; // 1h
}
