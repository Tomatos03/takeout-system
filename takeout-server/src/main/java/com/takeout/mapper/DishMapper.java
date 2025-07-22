package com.takeout.mapper;

import com.github.pagehelper.Page;
import com.takeout.annotation.AutoFill;
import com.takeout.entity.Dish;
import com.takeout.enums.SqlOperationType;
import com.takeout.vo.DishVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
@Mapper
public interface DishMapper {
    Dish queryById(Long id);

    @AutoFill(SqlOperationType.ADD)
    void add(Dish dish);

    Page<DishVO> pageQuery(String name, Integer categoryId, Integer status);

    void batchDelete(List<Long> ids);

    void deleteById(Long id);

    @AutoFill(SqlOperationType.UPDATE)
    void update(Dish dish);

    List<Dish> queryByCategoryId(Integer categoryId);
}