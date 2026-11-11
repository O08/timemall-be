package com.norm.timemall.app.base.util.zoho;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="zoho.transactional-email")
@PropertySource("classpath:zoho.properties")
public class ZohoTransactionalEmailResource {
  private String postUrl;
  private String authorization;
  private String noReplayAccount;
  private String accountName;
}
