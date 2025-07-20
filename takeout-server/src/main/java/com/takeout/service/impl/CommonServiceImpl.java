package com.takeout.service.impl;

import com.takeout.properties.COSProperties;
import com.takeout.service.CommonService;
import com.takeout.util.COSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    COSUtil cosUtil;
    @Autowired
    COSProperties cosProperties;

    @Override
    public String upload(MultipartFile file) {
        return cosUtil.uploadFile(cosProperties, file);
    }
}
