package com.takeout.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
@Data
@Component
@ConfigurationProperties("wechat")
public class WeChatProperties {
    private String appid;
    private String secret;
}
