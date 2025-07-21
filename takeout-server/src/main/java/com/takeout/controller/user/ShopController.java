package com.takeout.controller.user;

import com.takeout.service.ShopService;
import com.takeout.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
@Slf4j
// 默认使用类名第一个单词小写作为bean名称, 这里与admin包下的ShopController冲突, 必须手动命名
@RestController("userShopController")
@RequestMapping("/user/shop")
public class ShopController {
    @Autowired
    ShopService shopService;

    @GetMapping("/status")
    public Result<Integer> queryStatus() {
        Integer status = shopService.status();
        return Result.success(status);
    }
}
