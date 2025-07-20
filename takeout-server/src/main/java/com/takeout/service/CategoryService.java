package com.takeout.service;

import com.takeout.vo.CategoryVO;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
public interface CategoryService {
    List<CategoryVO> queryByType(int type);
}
