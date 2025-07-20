package com.takeout.vo;

import com.takeout.entity.DishFlavor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
@Data
public class DishVO {
    private Long categoryId; // 菜品分类id
    private String description; // 描述信息
    private List<DishFlavor> flavors = new ArrayList<>(); // 菜品关联的口味列表
    private Long id; // 菜品主键
    private String image; // 图片地址
    private String name; // 菜品名称
    private Double price; // 菜品价格
    private Integer status; // 状态（0停售，1起售）
    private String categoryName;
    private LocalDateTime updateTime;
}
