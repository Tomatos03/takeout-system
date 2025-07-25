package com.takeout.dto.page;

import lombok.Data;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Data
public class CategoryPageQueryDTO extends PageQueryDTO{
    //分类名称
    private String name;
    //分类类型 1菜品分类  2套餐分类
    private Integer type;
}