package com.takeout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.takeout.constant.DishStatusConst;
import com.takeout.constant.MessageConst;
import com.takeout.constant.RedisConst;
import com.takeout.dto.DishDTO;
import com.takeout.dto.page.DishPageQueryDTO;
import com.takeout.entity.Dish;
import com.takeout.entity.DishFlavor;
import com.takeout.exception.DeletionNotAllowedException;
import com.takeout.mapper.DishFlavorMapper;
import com.takeout.mapper.DishMapper;
import com.takeout.mapper.SetMealDishMapper;
import com.takeout.service.DishService;
import com.takeout.util.PageResult;
import com.takeout.vo.DishVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
@Service
public class DishServiceImpl implements DishService {
    @Autowired
    DishMapper dishMapper;

    @Autowired
    DishFlavorMapper dishFlavorMapper;

    @Autowired
    SetMealDishMapper setMealDishMapper;

    @Autowired
    RedisTemplate<String, List<DishVO>> redisTemplate;

    @Override
    public DishVO queryById(Long id) {
        Dish dish = dishMapper.queryById(id);
        List<DishFlavor> flavors = dishFlavorMapper.queryByDishId(id);

        DishVO dishVO = new DishVO();
        BeanUtil.copyProperties(dish, dishVO);
        dishVO.setFlavors(flavors);

        return dishVO;
    }

    @Override
    public void add(DishDTO dishDTO) {
        Dish dish = BeanUtil.copyProperties(dishDTO, Dish.class);
        dishMapper.add(dish);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors == null || flavors.isEmpty()) {
            return;
        }

        Long dishId = dishDTO.getId();
        flavors.forEach(dishFlavor -> {
            dishFlavor.setDishId(dishId);
            dishFlavorMapper.add(dishFlavor);
        });

        String key = RedisConst.DISH_KEY + dishDTO.getCategoryId();
        cleanCache(key);
    }

    @Override
    public PageResult<DishVO> pageQuery(DishPageQueryDTO dishQueryDTO) {
        int pageNum = dishQueryDTO.getPage();
        int pageSize = dishQueryDTO.getPageSize();
        String dishName = dishQueryDTO.getName();
        Integer categoryId = dishQueryDTO.getCategoryId();
        Integer status = dishQueryDTO.getStatus();

        PageHelper.startPage(pageNum, pageSize);

        Page<DishVO> dishVOPage = dishMapper.pageQuery(dishName, categoryId, status);

        PageResult<DishVO> dishPageResult = new PageResult<>();
        dishPageResult.setTotal(dishVOPage.getTotal());
        dishPageResult.setRecords(dishVOPage.getResult());

        return dishPageResult;
    }

    /**
     * 批量删除菜品, 以及关联数据
     *
     * @param ids 多个菜品id
     * @return void
     * @since : 1.0
     * @author : Tomatos
     * @date : 2025/7/20 22:25
     */
    @Override
    @Transactional
    public void batchDelete(List<Long> ids) {
        // 判断菜品是否还在销售, 如果没有在销售,无法删除
        ids.forEach(this::checkIfDishIsOnSale);
        // 被套餐关联无法删除
        ids.forEach(this::checkIfRelatedPackage);
        // 删除菜品表数据数据, 以及关联品味数据
        dishMapper.batchDelete(ids);
        dishFlavorMapper.batchDeleteByDishIds(ids);

        cleanCache(RedisConst.DISH_KEY + "*");
    }

    @Override
    public void update(DishDTO dishDTO) {
        Dish dish = BeanUtil.copyProperties(dishDTO, Dish.class);
        dishMapper.update(dish);


        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null)
            flavors.forEach(this::updateFlavor);

        cleanCache(RedisConst.DISH_KEY + "*");
    }

    @Override
    public List<DishVO> getDishList(Long categoryId) {
        String dishKey = RedisConst.DISH_KEY + categoryId;
        List<DishVO> dishVOS = redisTemplate.opsForValue().get(dishKey);
        if (dishVOS != null) {
            return dishVOS;
        }

        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(DishStatusConst.START_SELL);

        dishVOS = dishMapper.queryDishWithFlavors(dish);
        // 缓存查询的菜品信息
        redisTemplate.opsForValue()
                     .set(dishKey, dishVOS);
        return dishVOS;
    }

    @Override
    public void startOrStop(Integer status, Integer id) {
        cleanCache("dish_*");
        
        //
    }

    private void updateFlavor(DishFlavor dishFlavor) {
        dishFlavorMapper.update(dishFlavor);
    }

    private void checkIfRelatedPackage(Long id) {
        long count = setMealDishMapper.queryNumByDishID(id);
        if (count != 0)
            throw new DeletionNotAllowedException(MessageConst.DISH_ASSOCIATED);
    }

    private void checkIfDishIsOnSale(Long id) {
        Dish dish = dishMapper.queryById(id);
        if (dish.getStatus() == DishStatusConst.STOP_SELL)
            throw new DeletionNotAllowedException(MessageConst.DISH_SELLING);
    }

    private void cleanCache(String pattern) {
        // KEYS 会全量扫描所有 key，数据量大时会阻塞 Redis，影响线上性能
        // 得到满足相关模式的所有key
        Set<String> keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }
}
