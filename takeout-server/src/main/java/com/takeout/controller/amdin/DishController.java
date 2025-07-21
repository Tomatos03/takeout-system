package com.takeout.controller.amdin;

import com.takeout.dto.DishDTO;
import com.takeout.dto.DishPageQueryDTO;
import com.takeout.entity.Dish;
import com.takeout.service.DishServiceService;
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
@RestController
@RequestMapping("/admin/dish")
public class DishController {
    @Autowired
    private DishServiceService dishServiceService;

    @PostMapping
    public Result add(@RequestBody DishDTO dishDTO) {
        log.info("添加菜品{}", dishDTO);
        dishServiceService.add(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult<DishVO>> pageQuery(DishPageQueryDTO pageQueryDTO) {
        log.info("分页查询菜品{}", pageQueryDTO);
        PageResult<DishVO> dishPageResult = dishServiceService.pageQuery(pageQueryDTO);
        return Result.success(dishPageResult);
    }

    @DeleteMapping
    public Result deletes(@RequestParam List<Long> ids) {
        log.info("菜品批量删除{}", ids);
        dishServiceService.batchDelete(ids);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<DishVO> queryById(@PathVariable Long id) {
        log.info("根据id[{}]查询菜品", id);
        DishVO dishVO = dishServiceService.queryById(id);
        return Result.success(dishVO);
    }

    @PutMapping
    public Result<Dish> update(@RequestBody DishDTO dishDTO) {
        log.info("更新菜品信息{}", dishDTO);
        dishServiceService.update(dishDTO);
        return Result.success();
    }
}
