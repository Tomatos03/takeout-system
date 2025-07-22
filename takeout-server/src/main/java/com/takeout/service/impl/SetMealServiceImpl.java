package com.takeout.service.impl;

import com.takeout.entity.Dish;
import com.takeout.entity.SetMeal;
import com.takeout.mapper.DishMapper;
import com.takeout.mapper.SetMealDishMapper;
import com.takeout.mapper.SetMealMapper;
import com.takeout.service.SetMealService;
import com.takeout.vo.SetMealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/22
 */
@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    SetMealMapper setMealMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    SetMealDishMapper setMealDishMapper;

    @Override
    public List<SetMeal> queryByCategoryId(Integer categoryId) {
        List<SetMeal> setMeals = setMealMapper.queryByCategoryId(categoryId);
        return setMeals;
    }

    @Override
    public List<SetMealVO> queryById(Integer categoryId) {
        List<Dish> dishes = dishMapper.queryByCategoryId(categoryId);
        List<SetMealVO> setMealVOS = new ArrayList<>(dishes.size());
        for (Dish dish : dishes) {
            SetMealVO setMealVO = new SetMealVO();
            setMealVO.setName(dish.getName());
            setMealVO.setDescription(dish.getDescription());
            setMealVO.setImage(dish.getImage());

            Integer copies = setMealDishMapper.queryCopiesById(categoryId, dish.getId());
            setMealVO.setCopies(copies);

            setMealVOS.add(setMealVO);
        }

        return setMealVOS;
    }
}
