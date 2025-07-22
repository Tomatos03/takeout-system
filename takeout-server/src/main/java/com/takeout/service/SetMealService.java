package com.takeout.service;

import com.takeout.dto.SetMealDTO;
import com.takeout.dto.page.SetMealPageQueryDTO;
import com.takeout.entity.SetMeal;
import com.takeout.util.PageResult;
import com.takeout.vo.SetMealVO;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/22
 */
public interface SetMealService {
    List<SetMeal> query(SetMealDTO setMealDTO);

    List<SetMealVO> queryById(Long categoryId);


    /**
     * 新增套餐
     * @param setmealDTO
     */
    void saveWithDish(SetMealDTO setmealDTO);

    /**
     * 分页查询
     *
     * @param setmealPageQueryDTO
     * @return
     */
    PageResult<SetMealVO> pageQuery(SetMealPageQueryDTO setmealPageQueryDTO);

    /**
     * 批量删除套餐
     * @param ids
     */
    void deleteBatch(List<Long> ids);

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    SetMealVO getByIdWithDish(Long id);

    /**
     * 修改套餐
     * @param setmealDTO
     */
    void update(SetMealDTO setmealDTO);

    /**
     * 套餐起售停售
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);

    /**
     * 条件查询
     * @param setmeal
     * @return
     */
    List<SetMeal> list(SetMeal setmeal);
}
