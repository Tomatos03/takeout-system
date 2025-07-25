package com.takeout.mapper;

import com.takeout.annotation.AutoFill;
import com.takeout.entity.DishFlavor;
import com.takeout.enums.SqlOperationType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
@Mapper
public interface DishFlavorMapper {
    List<DishFlavor> queryByDishId(Long id);

    @AutoFill(SqlOperationType.ADD)
    void add(DishFlavor dishFlavor);

    void batchDeleteByDishIds(List<Long> ids);

    @AutoFill(SqlOperationType.UPDATE)
    void update(DishFlavor dishFlavor);
}
