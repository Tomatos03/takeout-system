package com.takeout.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
@Data
public class CategoryVO {
    private Long id;
    private Integer type;
    private String name;
    private Integer sort;
    private LocalDateTime updateTime;
    private Long updateUser;
    private LocalDateTime createTime;
    private Long createUser;
}
