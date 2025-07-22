package com.takeout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.takeout.constant.DishStatusConst;
import com.takeout.constant.MessageConst;
import com.takeout.constant.SetMealStatusConst;
import com.takeout.dto.SetMealDTO;
import com.takeout.dto.page.SetMealPageQueryDTO;
import com.takeout.entity.Dish;
import com.takeout.entity.SetMeal;
import com.takeout.entity.SetMealDish;
import com.takeout.exception.DeletionNotAllowedException;
import com.takeout.exception.SetMealEnableFailedException;
import com.takeout.mapper.DishMapper;
import com.takeout.mapper.SetMealDishMapper;
import com.takeout.mapper.SetMealMapper;
import com.takeout.service.SetMealService;
import com.takeout.util.PageResult;
import com.takeout.vo.SetMealVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/22
 */
@Service
public class SetMealServiceImpl implements SetMealService {
    @Autowired
    SetMealMapper setMealMapper;

    @Autowired
    SetMealDishMapper setMealDishMapper;

    @Autowired
    DishMapper dishMapper;

    @Override
    public List<SetMeal> query(SetMealDTO setMealDTO) {
        List<SetMeal> setMeals = setMealMapper.queryByCategoryId(setMealDTO);
        return setMeals;
    }

    @Override
    public List<SetMealVO> queryById(Long categoryId) {
        Dish dish = new Dish();
        dish.setCategoryId(categoryId);
        dish.setStatus(DishStatusConst.START_SELL);

        List<SetMealVO> setMealVOS = setMealDishMapper.queryWithCopies(dish);
        return setMealVOS;
    }

    @Override
    public void saveWithDish(SetMealDTO setmealDTO) {
        SetMeal setMeal = BeanUtil.copyProperties(setmealDTO, SetMeal.class);
        // 执行完成insert之后自动生成id
        setMealMapper.insert(setMeal);

        Long setMealId = setMeal.getId();

        List<SetMealDish> setMealDishes = setmealDTO.getSetmealDishes();
        setMealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setMealId));

        // 保存套餐和菜品的关联关系
        setMealDishMapper.insertBatch(setMealDishes);
    }

    @Override
    public PageResult<SetMealVO> pageQuery(SetMealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        Page<SetMealVO> page = setMealDishMapper.pageQuery(setmealPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void deleteBatch(List<Long> ids) {
//        起售中的套餐不能删除
        ids.forEach(id->{
            SetMeal setMeal = setMealMapper.queryById(id);
            if (setMeal.getStatus() != SetMealStatusConst.START_SELL)
                throw new DeletionNotAllowedException(MessageConst.SETMEAL_ON_SALE);
        });

        ids.forEach(id->{
            setMealMapper.deleteById(id);
            setMealDishMapper.deleteBySetMaleId(id);
        });
    }

    @Override
    public SetMealVO getByIdWithDish(Long id) {
        // 查询套餐基本信息
        SetMeal setMeal = setMealMapper.queryById(id);
        SetMealVO setMealVO = BeanUtil.copyProperties(setMeal, SetMealVO.class);

        // 根据套餐信息查询菜品信
        List<SetMealDish> setmealDishList = setMealDishMapper.queryBySetMealId(id);
        setMealVO.setSetmealDishes(setmealDishList);
        return setMealVO;
    }


    @Override
    public void update(SetMealDTO setmealDTO) {
        SetMeal setMeal = BeanUtil.copyProperties(setmealDTO, SetMeal.class);
        setMealMapper.update(setMeal);

        Long setMealId = setmealDTO.getId();
        setMealDishMapper.deleteBySetMaleId(setMealId);

        List<SetMealDish> setMealDishes = setmealDTO.getSetmealDishes();
        setMealDishes.forEach(setmealDish -> setmealDish.setSetmealId(setMealId));
        setMealDishMapper.insertBatch(setMealDishes);
    }

    /**
     * 尝试启用（起售）某个菜品
     * @param dishId 菜品ID
     * @throws RuntimeException 如果菜品无法启用
     */
    public void tryEnableDish(Long setMealId) {
        List<Dish> dishList = dishMapper.queryBySetMealId(setMealId);

        if (dishList == null || dishList.isEmpty())
            return;

        dishList.forEach(dish -> {
            if (DishStatusConst.STOP_SELL == dish.getStatus())  // 有停售商品
                throw new SetMealEnableFailedException(MessageConst.SETMEAL_ENABLE_FAILED);
        });
    }

    @Override
    public void startOrStop(Integer status, Long id) {
        if (status == SetMealStatusConst.START_SELL)
            tryEnableDish(id);

        SetMeal setmeal = SetMeal .builder()
                                  .id(id)
                                  .status(status)
                                  .build();
        setMealMapper.update(setmeal);
    }

    @Override
    public List<SetMeal> list(SetMeal setmeal) {
        return setMealMapper.list();
    }
}
