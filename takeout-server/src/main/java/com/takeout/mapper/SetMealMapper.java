package com.takeout.mapper;

import com.takeout.dto.SetMealDTO;
import com.takeout.entity.Dish;
import com.takeout.entity.SetMeal;
import com.takeout.vo.SetMealVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : Tomatos
 * @date : 2025/7/22
 */

@Mapper
public interface SetMealMapper {
    List<SetMeal> queryByCategoryId(SetMealDTO setMealDTO);

    List<SetMealVO> queryWithCopies(Dish dish);

    Integer countByCategoryId(Long categoryId);

    SetMeal queryById(Long id);

    Integer countByMap(Map map);

    void insert(SetMeal setMeal);

    List<SetMeal> list();

    void update(SetMeal setMeal);

    void deleteById(Long id);
}
