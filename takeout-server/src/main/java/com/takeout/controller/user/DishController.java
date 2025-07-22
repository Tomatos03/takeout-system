package com.takeout.controller.user;

import com.takeout.service.DishService;
import com.takeout.util.Result;
import com.takeout.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/21
 */
@Slf4j
@RestController("userDishController")
@RequestMapping("/user/dish")
public class DishController {
    @Autowired
    DishService dishService;

    @GetMapping("/list")
    public Result<List<DishVO>> queryList(@RequestParam Integer categoryId) {
        List<DishVO> dishList = dishService.getDishList(categoryId);
        return Result.success(dishList);
    }
}
