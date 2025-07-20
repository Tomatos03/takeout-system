package com.takeout.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.takeout.properties.COSProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
@Slf4j
@Component
public class COSUtil {
    private final COSClient cosClient;
    // 构造器注入
    public COSUtil(COSClient cosClient) {
        this.cosClient = cosClient;
    }

    public void uploadFile(COSProperties cosProperties, String path) {
        File localFile = new File(path);
        String bucketName = cosProperties.getBucketName();
        String key = cosProperties.getStoragePath();
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
    }

    public String uploadFile(COSProperties cosProperties, MultipartFile file) {
        log.info("开始上传文件...");
        // 获取文件名
        String key = cosProperties.getStoragePath() + "/" + System.currentTimeMillis();
        // 获取存储桶和目标路径
        String bucketName = cosProperties.getBucketName();
        // MultipartFile 转 InputStream
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            log.info("upload文件发生错误:{}", e.getMessage());
            return "";
        }
        // 构造 PutObjectRequest
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(file.getSize());
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream,
                                                                 objectMetadata);

        // 上传
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        log.info("上传文件成功...");

        String region = cosProperties.getRegion();
        String pathFile = String.format("https://%s.cos.%s.myqcloud.com%s", bucketName, region,
                                      key);
        log.info(pathFile);
        return pathFile;
    }
}
