package com.takeout.vo;

import com.takeout.entity.SetMealDish;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/22
 */
@Data
public class SetMealVO {
    private Long id;
    //分类id
    private Long categoryId;

    //套餐名称
    private String name;

    //套餐价格
    private BigDecimal price;

    //状态 0:停用 1:启用
    private Integer status;

    //描述信息
    private String description;

    //图片
    private String image;

    //更新时间
    private LocalDateTime updateTime;

    //分类名称
    private String categoryName;

    //套餐和菜品的关联关系
    private List<SetMealDish> setmealDishes = new ArrayList<>();
}
