package com.takeout.service;

import com.takeout.dto.DishDTO;
import com.takeout.dto.DishPageQueryDTO;
import com.takeout.util.PageResult;
import com.takeout.vo.DishVO;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
public interface DishService {
    DishVO queryById(Long id);

    void add(DishDTO dishDTO);

    PageResult<DishVO> pageQuery(DishPageQueryDTO dishQueryDTO);

    void batchDelete(List<Long> ids);

    void update(DishDTO dishDTO);

    List<DishVO> getDishList(Integer categoryId);
}
