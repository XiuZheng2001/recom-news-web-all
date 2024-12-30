package com.spring.controller;

import com.spring.pojo.Category;
import com.spring.pojo.Result;
import com.spring.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public Result add(@RequestBody @Validated Category category) {
        // 先查询看是否已经存在，再添加
        categoryService.add(category);
        return Result.success();
    }
    @GetMapping
    public Result<List<Category>> findByUserId() {
        List<Category> res = categoryService.list();
        return Result.success(res);
    }
    @GetMapping("/detail")
    public Result<Category> detail(int id) {
        Category c = categoryService.findById(id);
        return Result.success(c);
    }

    @PutMapping
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }

    @DeleteMapping
    public Result delete(Integer id){
        categoryService.deleteById(id);
        return Result.success();
    }
}
