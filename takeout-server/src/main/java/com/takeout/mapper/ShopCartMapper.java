package com.takeout.mapper;

import com.takeout.entity.ShopCart;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Mapper
public interface ShopCartMapper {
    List<ShopCart> list(ShopCart shopCart);

    void updateNum(ShopCart shopCart);

    void insert(ShopCart shopCart);

    void deleteByUserId(Long userId);
}
