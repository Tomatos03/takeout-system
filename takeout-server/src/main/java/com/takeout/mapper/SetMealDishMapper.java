package com.takeout.mapper;

import com.github.pagehelper.Page;
import com.takeout.dto.page.SetMealPageQueryDTO;
import com.takeout.entity.Dish;
import com.takeout.entity.SetMealDish;
import com.takeout.vo.SetMealVO;
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

    List<SetMealVO> queryWithCopies(Dish dish);

    Page<SetMealVO> pageQuery(SetMealPageQueryDTO setmealPageQueryDTO);

    void insertBatch(List<SetMealDish> setMealDishes);

    void deleteBySetMaleId(Long setMealId);

    List<SetMealDish> queryBySetMealId(Long id);
}
