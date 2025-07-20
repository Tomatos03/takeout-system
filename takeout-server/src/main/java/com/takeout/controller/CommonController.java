package com.takeout.controller;

import com.takeout.service.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
@Slf4j
@RestController
@RequestMapping("/admin/common")
public class CommonController {
    @Autowired
    CommonService commonService;

//    @PostMapping("/upload")
//    public Result upload(@RequestParam MultipartFile file) {
//        String filePath = commonService.upload(file);
//        return Result.success(filePath);
//    }
}
