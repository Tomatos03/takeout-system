package com.takeout.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
public interface CommonService {
    String upload(MultipartFile file);
}
