package com.norm.timemall.app.base.util.shlianlu;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="lianlu.sms")
@PropertySource("classpath:lianlu.properties")
public class LianluResource {
    private String mchId;
    private String appId;
    private String appKey;
}
