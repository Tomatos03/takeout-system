package com.takeout.service;

import com.takeout.dto.CategoryDTO;
import com.takeout.dto.page.CategoryPageQueryDTO;
import com.takeout.util.PageResult;
import com.takeout.vo.CategoryVO;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
public interface CategoryService {
    List<CategoryVO> queryByType(int type);

    /**
     * 新增分类
     * @param categoryDTO
     */
    void save(CategoryDTO categoryDTO);

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 根据id删除分类
     * @param id
     */
    void deleteById(Long id);

    /**
     * 修改分类
     * @param categoryDTO
     */
    void update(CategoryDTO categoryDTO);

    /**
     * 启用、禁用分类
     * @param status
     * @param id
     */
    void startOrStop(Integer status, Long id);
}
