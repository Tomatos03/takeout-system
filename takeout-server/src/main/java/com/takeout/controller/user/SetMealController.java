package com.takeout.controller.user;

import com.takeout.entity.SetMeal;
import com.takeout.service.SetMealService;
import com.takeout.util.Result;
import com.takeout.vo.SetMealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/22
 */
@Slf4j
@RequestMapping("/user/setmeal")
@RestController("userSetMealController")
public class SetMealController {
    @Autowired
    SetMealService setMealService;

    @GetMapping("/list")
    public Result<List<SetMeal>> queryList(@RequestParam Integer categoryId) {
        List<SetMeal> setMeals = setMealService.queryByCategoryId(categoryId);
        return Result.success(setMeals);
    }

    @GetMapping("/dish/{id}")
    public Result<List<SetMealVO>> queryById(@PathVariable("id") Integer categoryId) {
        List<SetMealVO> setMealVOS = setMealService.queryById(categoryId);
        return Result.success(setMealVOS);
    }
}
