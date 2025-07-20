package com.takeout.dto;

import lombok.Data;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
@Data
public class DishPageQueryDTO extends PageQueryDTO{
    private String name;
    private Integer categoryId;
    private Integer status;
}
