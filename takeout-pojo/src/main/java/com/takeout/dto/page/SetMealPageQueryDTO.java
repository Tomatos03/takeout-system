package com.takeout.dto.page;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
public class SetMealPageQueryDTO extends PageQueryDTO{
    private int page;

    private int pageSize;

    private String name;

    //分类id
    private Integer categoryId;

    //状态 0表示禁用 1表示启用
    private Integer status;
}
