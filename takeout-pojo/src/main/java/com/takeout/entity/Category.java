package com.takeout.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
@Data
public class Category {
    private Long id;
    private Integer type;
    private String name;
    private Integer sort;
    private Integer status;
    private LocalDateTime updateTime;
    private Long updateUser;
    private LocalDateTime createTime;
    private Long createUser;
}