package com.takeout.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.takeout.context.LoginContext;
import com.takeout.dto.ShopCartDTO;
import com.takeout.entity.Dish;
import com.takeout.entity.SetMeal;
import com.takeout.entity.ShopCart;
import com.takeout.mapper.DishMapper;
import com.takeout.mapper.SetMealMapper;
import com.takeout.mapper.ShopCartMapper;
import com.takeout.service.ShopCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Service
public class ShopCartServiceImpl implements ShopCartService {
    @Autowired
    ShopCartMapper shopCartMapper;

    @Autowired
    DishMapper dishMapper;

    @Autowired
    SetMealMapper setMealMapper;

    @Override
    public void addShopToCart(ShopCartDTO shopCartDTO) {
        ShopCart shopCart = BeanUtil.copyProperties(shopCartDTO, ShopCart.class);
        shopCart.setUserId(LoginContext.getCurrentId());

        List<ShopCart> list = shopCartMapper.list(shopCart);
        boolean shopIncluded = list != null && !list.isEmpty();
        if (shopIncluded) {
            shopCart.setNumber(shopCart.getNumber() + 1);
            shopCartMapper.updateNum(shopCart);
            return;
        }

        Long dishId = shopCart.getDishId();
        if (dishId != null) {
            // 添加到购物车的是菜品
            Dish dish = dishMapper.queryById(dishId);
            shopCart.setName(dish.getName());
            shopCart.setImage(dish.getImage());
            shopCart.setAmount(dish.getPrice());
        } else {
            // 添加到购物车的是套餐
            SetMeal setMeal = setMealMapper.queryById(shopCartDTO.getSetmealId());
            shopCart.setName(setMeal.getName());
            shopCart.setImage(setMeal.getImage());
            shopCart.setAmount(setMeal.getPrice());
        }

        shopCart.setNumber(1);
        shopCart.setCreateTime(java.time.LocalDateTime.now());

        shopCartMapper.insert(shopCart);
    }

    @Override
    public List<ShopCart> showShopCart() {
        ShopCart shopCart = new ShopCart();
        shopCart.setUserId(LoginContext.getCurrentId());

        List<ShopCart> list = shopCartMapper.list(shopCart);
        return list;
    }

    @Override
    public void cleanShopCart() {
        shopCartMapper.deleteByUserId(LoginContext.getCurrentId());
    }
}
