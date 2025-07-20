package com.takeout.mapper;

import com.takeout.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
@Mapper
public interface CategoryMapper {
    List<Category> query(int type);
}
