package com.takeout.dto;

import com.takeout.entity.SetMealDish;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Data
public class SetMealDTO {
    private Long id;
    //分类id
    private Long categoryId;
    //套餐名称
    private String name;
    //套餐价格
    private Double price;
    //状态 0:停用 1:启用
    private Integer status;
    //描述信息
    private String description;
    //图片
    private String image;
    //套餐菜品关系
    private List<SetMealDish> setmealDishes = new ArrayList<>();
}
