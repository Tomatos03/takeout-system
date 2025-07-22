package com.takeout.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "token")
public class JwsProperties {
    private Admin admin;
    private User user;

    @Data
    public static class Admin {
        private Long expiration;
        private String prefix;
        private String secretKey;
    }

    @Data
    public static class User {
        private Long expiration;
        private String prefix;
        private String secretKey;
    }
}
