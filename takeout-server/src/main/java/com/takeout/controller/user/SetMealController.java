package com.takeout.controller.user;

import com.takeout.constant.SetMealStatusConst;
import com.takeout.dto.SetMealDTO;
import com.takeout.dto.page.SetMealPageQueryDTO;
import com.takeout.entity.SetMeal;
import com.takeout.service.SetMealService;
import com.takeout.util.PageResult;
import com.takeout.util.Result;
import com.takeout.vo.SetMealVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = "setmealCache", key = "#categoryId")
    public Result<List<SetMeal>> queryList(@RequestParam Long categoryId) {
        SetMealDTO setMealDTO = new SetMealDTO();
        setMealDTO.setCategoryId(categoryId);
        setMealDTO.setStatus(SetMealStatusConst.START_SELL);

        List<SetMeal> setMeals = setMealService.query(setMealDTO);
        return Result.success(setMeals);
    }

    @GetMapping("/dish/{id}")
    public Result<List<SetMealVO>> queryById(@PathVariable("id") Long categoryId) {
        List<SetMealVO> setMealVOS = setMealService.queryById(categoryId);
        return Result.success(setMealVOS);
    }

    /**
     * 新增套餐
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @CacheEvict(cacheNames = "setmealCache",key = "#setmealDTO.categoryId")
    public Result save(@RequestBody SetMealDTO setmealDTO) {
        setMealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    public Result<PageResult> page(SetMealPageQueryDTO setmealPageQueryDTO) {
        PageResult pageResult = setMealService.pageQuery(setmealPageQueryDTO);
        return Result.success();
    }

    /**
     * 批量删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids) {
        setMealService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 根据id查询套餐
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<SetMealVO> getById(@PathVariable Long id) {
        SetMealVO setmealVO = setMealService.getByIdWithDish(id);
        return Result.success();
    }

    /**
     * 修改套餐
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetMealDTO setmealDTO) {
        setMealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * 套餐起售停售
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status, Long id) {
        setMealService.startOrStop(status, id);
        return Result.success();
    }
}
