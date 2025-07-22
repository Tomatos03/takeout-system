package com.takeout.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
@Mapper
public interface SetMealDishMapper {
      long queryNumByDishID(Long id);

      void batchDelete(List<Long> ids);

      Integer queryCopiesById(Integer setMealId, Long dishId);
}
