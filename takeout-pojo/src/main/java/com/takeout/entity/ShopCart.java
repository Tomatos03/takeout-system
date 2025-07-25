package com.takeout.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopCart {
    private Long id;
    private String name;
    private String image;
    private Long userId;
    private Long dishId;
    private Long setmealId;
    private String dishFlavor;
    private Integer number;
    private Double amount;
    private LocalDateTime createTime;
}