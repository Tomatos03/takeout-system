package com.takeout.entity;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : Tomatos
 * @date : 2025/7/22
 */
@Data
@Builder
public class SetMeal {
    private Long id;
    private Long categoryId;
    private String name;
    private Double price;
    private Integer status;
    private String description;
    private String image;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
}