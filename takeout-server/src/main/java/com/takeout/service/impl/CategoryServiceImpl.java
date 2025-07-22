package com.takeout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.takeout.constant.MessageConst;
import com.takeout.constant.StatusConst;
import com.takeout.context.LoginContext;
import com.takeout.dto.CategoryDTO;
import com.takeout.dto.page.CategoryPageQueryDTO;
import com.takeout.entity.Category;
import com.takeout.exception.DeletionNotAllowedException;
import com.takeout.mapper.CategoryMapper;
import com.takeout.mapper.DishMapper;
import com.takeout.mapper.SetMealMapper;
import com.takeout.service.CategoryService;
import com.takeout.util.PageResult;
import com.takeout.vo.CategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/20
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMealMapper setMealMapper;

    /**
     * 新增分类
     * @param categoryDTO
     */
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        //属性拷贝
        BeanUtils.copyProperties(categoryDTO, category);

        //分类状态默认为禁用状态0
        category.setStatus(StatusConst.DISABLE);

        //设置创建时间、修改时间、创建人、修改人
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        category.setCreateUser(LoginContext.getCurrentId());
        category.setUpdateUser(LoginContext.getCurrentId());

        categoryMapper.insert(category);
    }

    /**
     * 分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    public PageResult pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        int pageNum = categoryPageQueryDTO.getPage();
        int pageSize = categoryPageQueryDTO.getPageSize();
        PageHelper.startPage(pageNum, pageSize);

        //下一条sql进行分页，自动加入limit关键字分页
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * 根据id删除分类
     * @param id
     */
    public void deleteById(Long id) {
        //查询当前分类是否关联了菜品，如果关联了就抛出业务异常
        Integer count = dishMapper.countByCategoryId(id);
        if(count > 0)
            //当前分类下有菜品，不能删除
            throw new DeletionNotAllowedException(MessageConst.CATEGORY_HAS_DISH);

        //查询当前分类是否关联了套餐，如果关联了就抛出业务异常
        count = setMealMapper.countByCategoryId(id);
        if(count > 0)
            throw new DeletionNotAllowedException(MessageConst.CATEGORY_HAS_DISH);

        //删除分类数据
        categoryMapper.deleteById(id);
    }

    /**
     * 修改分类
     * @param categoryDTO
     */
    public void update(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO,category);

        //设置修改时间、修改人
        category.setUpdateTime(LocalDateTime.now());
        category.setUpdateUser(LoginContext.getCurrentId());

        categoryMapper.update(category);
    }

    /**
     * 启用、禁用分类
     * @param status
     * @param id
     */
    public void startOrStop(Integer status, Long id) {
        Category category = Category.builder()
                .id(id)
                .status(status)
                .updateTime(LocalDateTime.now())
                .updateUser(LoginContext.getCurrentId())
                .build();

        categoryMapper.update(category);
    }

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
