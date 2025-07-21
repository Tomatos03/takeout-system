package com.takeout.controller.amdin;

import com.takeout.constant.ShopStatusConst;
import com.takeout.service.ShopService;
import com.takeout.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */

@Slf4j
@RestController("adminShopController")
@RequestMapping("/admin/shop")
public class ShopController {
    @Autowired
    ShopService shopService;

    @GetMapping("/status")
    public Result<Integer> queryStatus() {
        Integer status = shopService.status();
        return Result.success(status);
    }

    @PutMapping("/{status}")
    public Result setStatus(@PathVariable int status) {
        log.info("设置店铺状态为{}", status == ShopStatusConst.SHOP_OPEN ? "营业" : "打烊");
        shopService.setStatus(status);
        return Result.success();
    }
}
