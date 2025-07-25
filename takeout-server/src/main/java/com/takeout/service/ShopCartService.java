package com.takeout.service;

import com.takeout.dto.ShopCartDTO;
import com.takeout.entity.ShopCart;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
public interface ShopCartService {
    void addShopToCart(ShopCartDTO shopCartDTO);

    List<ShopCart> showShopCart();

    void cleanShopCart();
}
