package com.takeout.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "token")
public class JwsProperties {
    // 以秒为单位
    private Long expiration;
    private String prefix;
    private String secretKey;
}
