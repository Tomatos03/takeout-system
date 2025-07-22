package com.takeout.mapper;

import com.takeout.entity.SetMeal;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/22
 */

@Mapper
public interface SetMealMapper {
    List<SetMeal> queryByCategoryId(Integer categoryId);
}
