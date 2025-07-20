package com.takeout.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
@Data
@Component
@ConfigurationProperties(prefix = "tencent-cloud-cos")
public class COSProperties {
    private String region;
    private String secretId;
    private String secretKey;
    private String bucketName;
    private String storagePath;
}
