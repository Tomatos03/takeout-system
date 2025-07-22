package com.takeout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.takeout.entity.Category;
import com.takeout.mapper.CategoryMapper;
import com.takeout.service.CategoryService;
import com.takeout.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> queryByType(int type) {
        List<Category> categories = categoryMapper.query(type);
        List<CategoryVO> categoryVOS = new ArrayList<>();
        for (Category category : categories) {
            CategoryVO categoryVO = BeanUtil.copyProperties(category, CategoryVO.class);
            categoryVOS.add(categoryVO);
        }
        return categoryVOS;
    }
}
