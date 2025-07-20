package com.takeout.config;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import com.takeout.properties.COSProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
@Configuration
public class COSConfig {
    @Bean
    public COSClient cosClient(COSProperties cosProperties) {
        String secretId = cosProperties.getSecretId();
        String secretKey = cosProperties.getSecretKey();
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);

        String regionName = cosProperties.getRegion();
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);

        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }
}
