package com.takeout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.takeout.entity.Category;
import com.takeout.mapper.CategoryMapper;
import com.takeout.service.CategoryService;
import com.takeout.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        return categories.stream()
                         .map(category -> BeanUtil.copyProperties(category, CategoryVO.class))
                         .collect(Collectors.toList());
    }
}
