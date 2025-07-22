package com.takeout.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
@Data
public class User {
    private Long id;
    private String openid;
    private String name;
    private String phone;
    private String sex;
    private String idNumber;
    private String avatar;
    private LocalDateTime createTime;
}
