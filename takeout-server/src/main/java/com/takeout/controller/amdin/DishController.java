package com.takeout.controller.amdin;

import com.takeout.dto.DishDTO;
import com.takeout.dto.page.DishPageQueryDTO;
import com.takeout.entity.Dish;
import com.takeout.service.DishService;
import com.takeout.util.PageResult;
import com.takeout.util.Result;
import com.takeout.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author : Tomatos
 * @date : 2025/7/19
 */
@Slf4j
@RestController("adminDishController")
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishService dishService;

    @PostMapping
    public Result add(@RequestBody DishDTO dishDTO) {
        log.info("添加菜品{}", dishDTO);
        dishService.add(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult<DishVO>> pageQuery(DishPageQueryDTO pageQueryDTO) {
        log.info("分页查询菜品{}", pageQueryDTO);
        PageResult<DishVO> dishPageResult = dishService.pageQuery(pageQueryDTO);
        return Result.success(dishPageResult);
    }

    @DeleteMapping
    public Result deletes(@RequestParam List<Long> ids) {
        log.info("菜品批量删除{}", ids);
        dishService.batchDelete(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<DishVO> queryById(@PathVariable Long id) {
        log.info("根据id[{}]查询菜品", id);
        DishVO dishVO = dishService.queryById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    public Result<Dish> update(@RequestBody DishDTO dishDTO) {
        log.info("更新菜品信息{}", dishDTO);
        dishService.update(dishDTO);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    public Result startOrStop(@PathVariable Integer status, @RequestParam Integer id) {
        dishService.startOrStop(status, id);
        return Result.success();
    }
}
