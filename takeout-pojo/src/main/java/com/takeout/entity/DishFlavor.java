package com.takeout.entity;

import lombok.Data;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
@Data
public class DishFlavor {
    private Long id;
    private Long dishId;
    private String name;
    private String value;
}
