package com.takeout.service;

import com.takeout.entity.SetMeal;
import com.takeout.vo.SetMealVO;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/22
 */
public interface SetMealService {
    List<SetMeal> queryByCategoryId(Integer categoryId);

    List<SetMealVO> queryById(Integer categoryId);
}
