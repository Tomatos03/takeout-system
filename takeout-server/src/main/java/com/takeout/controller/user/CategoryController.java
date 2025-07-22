package com.takeout.controller.user;

import com.takeout.service.CategoryService;
import com.takeout.util.Result;
import com.takeout.vo.CategoryVO;
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
@RestController("userCategoryController")
@RequestMapping("/user/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<CategoryVO>> query(@RequestParam Integer type) {
        List<CategoryVO> categoryVOS = categoryService.queryByType(type);
        return Result.success(categoryVOS);
    }
}
