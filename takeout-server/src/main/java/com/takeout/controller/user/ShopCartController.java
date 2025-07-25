package com.takeout.controller.user;

import com.takeout.dto.ShopCartDTO;
import com.takeout.entity.ShopCart;
import com.takeout.service.ShopCartService;
import com.takeout.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/23
 */
@Slf4j
@RestController
@RequestMapping("/user/shoppingCart")
public class ShopCartController {
    @Autowired
    ShopCartService shopCartService;

    @PostMapping("/add")
    public Result add(@RequestBody ShopCartDTO shopCartDTO) {
        shopCartService.addShopToCart(shopCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<List<ShopCart>> list() {
        List<ShopCart> list = shopCartService.showShopCart();
        return Result.success(list);
    }

    @DeleteMapping
    public Result clean() {
        shopCartService.cleanShopCart();
        return Result.success();
    }
}
